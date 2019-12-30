package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.parser.ExpressionParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Tokenizer implements ExpressionTokenizer.Tokenizer {
    private static final Map<Character, Function<Integer, Token>> RESERVED_TOKENS =
        new HashMap<Character, Function<Integer, Token>>(){{
            put('(', Token::parenthesisOpening);
            put(')', Token::parenthesisClosing);
            put(',', Token::comma);
        }};

    private final String expression;
    private final int expressionLength;
    private final ExpressionOperatorDefinitions operatorDefinitions;
    private int currentIndex;
    private int startIndex;
    private Token lastToken;

    Tokenizer(final String expression, final ExpressionOperatorDefinitions operatorDefinitions) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.operatorDefinitions = operatorDefinitions;
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
        if(Character.isSpaceChar(c)){
            lastToken = Token.EOE();
            return lastToken;
        }

        if(isDigit(c))
            return readNumber();

        if(RESERVED_TOKENS.containsKey(c))
            return getReservedDelimiter(c);

        if(isOperatorEligible(c))
            return readOperator();

        if(isConstEligible(c))
            return readConst();

        return getIdentifierToken();
    }

    private Token getIdentifierToken() {
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

        if((!hasNext() && Character.isSpaceChar(c)) || isOperatorEligible(c) || c == ',' || c == ')') {
            lastToken = Token.var(identifier, identifierStartIndex);
            return lastToken;
        }

        throw invalidChar(currentIndex);
    }

    private Token getReservedDelimiter(char c) {
        currentIndex++;
        lastToken = RESERVED_TOKENS.get(c).apply(startIndex);
        return lastToken;
    }

    private ExpressionParseException invalidChar(int index){
        return new ExpressionParseException(expression,index,"Invalid symbol at index %s",index+1);
    }

    private char nextValidChar() {
        char c = expression.charAt(currentIndex);

        while(Character.isSpaceChar(c)){
            currentIndex++;

            if(!hasNext())
                break;

            c = expression.charAt(currentIndex);
        }

        startIndex = currentIndex;
        return c;
    }

    private Token readNumber() {
        do {
            currentIndex++;
        } while (hasNext() && isDigit(expression.charAt(currentIndex)));

        lastToken = Token.number(expression.substring(startIndex,currentIndex),startIndex);
        return lastToken;
    }

    private String readIdentifier() {
        while (true){
            currentIndex++;

            if(!hasNext())
                break;

            char c = expression.charAt(currentIndex);
            if(isDelimiter(c))
                break;

            if(!isValidIdentifier(c,startIndex))
                throw new ExpressionParseException(expression,currentIndex,"Invalid identifier");
        }

        return expression.substring(startIndex,currentIndex);
    }

    private Token readConst() {
        while(true){
            currentIndex++;

            if(!hasNext())
                break;

            char c = expression.charAt(currentIndex);
            if(isDelimiter(c))
                break;

            if(!isConstEligible(c))
                throw new ExpressionParseException(expression, currentIndex,
                        "Invalid constant name. Constants must have only uppercase letters");
        }

        lastToken = Token.constant(expression.substring(startIndex,currentIndex),startIndex);
        return lastToken;
    }

    private Token readOperator() {
        while(true){
            currentIndex++;

            if(!hasNext())
                break;

            char c = expression.charAt(currentIndex);
            if(!isOperatorEligible(c) || !hasNext())
                break;
        }

        String operatorIdentifier = expression.substring(startIndex,currentIndex);
        if(operatorIdentifier.length() > 1) {
            while (!hasOperator(operatorIdentifier) && currentIndex > startIndex)
                operatorIdentifier = expression.substring(startIndex,--currentIndex);
        }

        lastToken = Token.operator(operatorIdentifier, startIndex);
        return lastToken;
    }

    private boolean isDelimiter(char c) {
        return c == ' ' || RESERVED_TOKENS.containsKey(c) || isOperatorEligible(c);
    }

    private boolean isConstEligible(char c) {
        return ('A' <= c && c <= 'Z')
                || (startIndex < currentIndex && (c == '_' || Character.isDigit(c))
        );
    }

    private boolean isValidIdentifier(char c, int startIndex) {
        return ('a' <= c && c <= 'z')
                || (startIndex < currentIndex && (c == '_' || Character.isDigit(c)));
    }

    private boolean isOperatorEligible(char c) {
        return c != ' ' && c != '_' && !RESERVED_TOKENS.containsKey(c) && !isDigit(c)
                && !('a' <= c && c <= 'z') && !('A' <= c && c <= 'Z');
    }

    private boolean hasOperator(String identifier){
        return operatorDefinitions.hasBinaryOperator(identifier)
                || operatorDefinitions.hasUnaryOperator(identifier);
    }

    private static boolean isDigit(char c){
        return ('0' <= c && c <= '9') || c == '.';
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{expression='" + expression + "'}";
    }
}
