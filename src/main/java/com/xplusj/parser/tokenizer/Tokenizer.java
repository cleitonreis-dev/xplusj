/*
 * MIT License
 *
 * Copyright (c) 2020 Cleiton Reis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.xplusj.parser.tokenizer;

import com.xplusj.parser.ExpressionParseException;

import java.util.Set;
import java.util.function.Function;

public class Tokenizer implements ExpressionTokenizer.Tokenizer {
    private final String expression;
    private final int expressionLength;
    private final Set<Character> allowedOperators;
    private final Function<String, Boolean> operatorFinder;

    private int currentIndex;
    private int startIndex;
    private Token lastToken;

    Tokenizer(final String expression,
              final Set<Character> allowedOperators,
              final Function<String,Boolean> operatorFinder) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.allowedOperators = allowedOperators;
        this.operatorFinder = operatorFinder;
    }

    @Override
    public Token getLastToken(){
        return lastToken;
    }

    @Override
    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public Token next(){
        if(!hasNext()){
            lastToken = Token.EOE();
            return lastToken;
        }

        char c = nextValidChar();
        if(c == ' '){
            lastToken = Token.EOE();
            return lastToken;
        }

        if(isNumberEligible(c))
            return readNumber();

        if(isReservedDelimiter(c))
            return getReservedDelimiter(c);

        if(allowedOperators.contains(c))
            return readOperator();

        if(isConstEligible(c))
            return readConst();

        if(isVarFuncEligible(c))
            return getVarOrFuncToken();

        throw invalidChar(currentIndex);
    }

    private Token getVarOrFuncToken() {
        int identifierStartIndex = startIndex;
        String identifier = readVarFuncIdentifier();

        if(!hasNext()) {
            lastToken = Token.var(identifier, identifierStartIndex);
            return lastToken;
        }

        char c = nextValidChar();
        currentIndex = startIndex;

        if(c == '(') {
            lastToken = Token.func(identifier, identifierStartIndex);
            return lastToken;
        }

        if(!hasNext() || isAllowedAfterValue(c)) {
            lastToken = Token.var(identifier, identifierStartIndex);
            return lastToken;
        }

        throw invalidChar(currentIndex);
    }

    private Token getReservedDelimiter(char c) {
        currentIndex++;
        lastToken = getReservedToken(c, startIndex, expression);
        return lastToken;
    }

    private ExpressionParseException invalidChar(int index){
        return new ExpressionParseException(expression,index,"Invalid symbol at index %s",index+1);
    }

    private char nextValidChar() {
        char c = expression.charAt(currentIndex);

        while(c == ' '){
            currentIndex++;

            if(!hasNext())
                break;

            c = expression.charAt(currentIndex);
        }

        startIndex = currentIndex;
        return c;
    }

    private Token readNumber() {
        while (hasNext() && isNumberValid(expression.charAt(currentIndex)))
            currentIndex++;

        char c = expression.charAt(hasNext() ? currentIndex : currentIndex-1);
        if(isNumberValid(c) || isAllowedAfterValue(c)){
            lastToken = Token.number(expression.substring(startIndex,currentIndex),startIndex);
            return lastToken;
        }

        throw new ExpressionParseException(expression,currentIndex,"Invalid number");
    }

    private String readVarFuncIdentifier() {
        while (hasNext() && isVarFuncValid(expression.charAt(currentIndex)))
            currentIndex++;

        char c = expression.charAt(hasNext() ? currentIndex : currentIndex-1);
        if(isVarFuncValid(c) || c == ' ' || allowedOperators.contains(c) || isReservedDelimiter(c))
            return expression.substring(startIndex,currentIndex);

        throw new ExpressionParseException(expression,currentIndex,"Invalid identifier");
    }

    private Token readConst() {
        while(hasNext() && isConstValid(expression.charAt(currentIndex)))
            currentIndex++;

        char c = expression.charAt(hasNext() ? currentIndex : currentIndex-1);
        if(isConstValid(c) || isAllowedAfterValue(c)) {
            lastToken = Token.constant(expression.substring(startIndex, currentIndex), startIndex);
            return lastToken;
        }

        throw new ExpressionParseException(expression, currentIndex,
            "Invalid constant name. Constants must have only uppercase letters");
    }

    private Token readOperator() {
        while(hasNext() && allowedOperators.contains(expression.charAt(currentIndex)))
            currentIndex++;

        String operatorIdentifier = expression.substring(startIndex, currentIndex);
        if(operatorIdentifier.length() > 1) {
            while (!operatorFinder.apply(operatorIdentifier) && currentIndex > startIndex)
                operatorIdentifier = expression.substring(startIndex,--currentIndex);
        }

        lastToken = Token.operator(operatorIdentifier, startIndex);
        return lastToken;
    }

    private boolean isAllowedAfterValue(char c) {
        return c == ' ' || allowedOperators.contains(c) || c == ',' || c == ')';
    }

    private static boolean isNumberEligible(char c){
        return Character.isDigit(c);
    }

    private static boolean isNumberValid(char c){
        return Character.isDigit(c) || c == '.';
    }

    private static boolean isConstEligible(char c) {
        return 'A' <= c && c <= 'Z';
    }

    private static boolean isConstValid(char c) {
        return ('A' <= c && c <= 'Z') || c == '_' || Character.isDigit(c);
    }

    private static boolean isVarFuncEligible(char c) {
        return 'a' <= c && c <= 'z';
    }

    private static boolean isVarFuncValid(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')
                || c == '_' || Character.isDigit(c);
    }

    private static boolean isReservedDelimiter(char c){
        return c == '(' || c == ')' || c == ',';
    }

    private static Token getReservedToken(char c, int index, String expression){
        if(c == '(')
            return Token.parenthesisOpening(index);

        if(c == ')')
            return Token.parenthesisClosing(index);

        if(c == ',')
            return Token.comma(index);

        throw new ExpressionParseException(expression, index, "Invalid reserved delimiter %s", c);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{expression='" + expression + "'}";
    }
}
