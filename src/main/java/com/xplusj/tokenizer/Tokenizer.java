package com.xplusj.tokenizer;

import com.xplusj.parser.ExpressionParseException;

import java.util.Set;

public class Tokenizer implements ExpressionTokenizer.Tokenizer {
    private final String expression;
    private final int expressionLength;
    private final Set<Character> allowedOperators;
    private final Set<String> definedOperators;

    private int currentIndex;
    private int startIndex;
    private Token lastToken;

    Tokenizer(final String expression,
              final Set<Character> allowedOperators,
              final Set<String> definedOperators) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.allowedOperators = allowedOperators;
        this.definedOperators = definedOperators;
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
        String identifier = readIdentifier();

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

        if((!hasNext() && c == ' ') || allowedOperators.contains(c) || c == ',' || c == ')') {
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
        if(isNumberValid(c) || c == ' ' || allowedOperators.contains(c) || c == ',' || c == ')'){
            lastToken = Token.number(expression.substring(startIndex,currentIndex),startIndex);
            return lastToken;
        }

        throw new ExpressionParseException(expression,currentIndex,"Invalid number");
    }

    private String readIdentifier() {
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
        if(isConstValid(c) || c == ' ' || allowedOperators.contains(c) || c == ',' || c == ')') {
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
            while (!definedOperators.contains(operatorIdentifier) && currentIndex > startIndex)
                operatorIdentifier = expression.substring(startIndex,--currentIndex);
        }

        lastToken = Token.operator(operatorIdentifier, startIndex);
        return lastToken;
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
