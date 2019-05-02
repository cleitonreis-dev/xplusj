package com.xplusj.engine.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExpressionTokenizer {
    private static final Map<Character, Function<Integer, Token>> RESERVED_TOKENS = new HashMap<>();
    static {
        RESERVED_TOKENS.put('(', Token::parenthesisOpening);
        RESERVED_TOKENS.put(')', Token::parenthesisClosing);
        RESERVED_TOKENS.put(',', Token::comma);
    }

    final String expression;
    private final int expressionLength;
    private final OperatorChecker operatorChecker;
    private int currentIndex;
    private int startIndex;
    Token lastToken;

    public ExpressionTokenizer(final String expression, final OperatorChecker operatorChecker) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.operatorChecker = operatorChecker;
    }

    public Token getLastToken(){
        return lastToken;
    }

    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

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

        if(isDigit(c))
            return readNumber();

        if(operatorChecker.isOperator(c))
            return readOperator();

        if(RESERVED_TOKENS.containsKey(c))
            return getReservedDelimiter(c);

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

        if((!hasNext() && c == ' ') || operatorChecker.isOperator(c) || c == ',' || c == ')') {
            lastToken = Token.var(identifier, identifierStartIndex);
            return lastToken;
        }

        throw invalidChar(c);
    }

    private Token readNumber() {
        while(true){
            currentIndex++;

            if(!hasNext() || !isDigit(expression.charAt(currentIndex)))
                break;
        }

        lastToken = Token.number(expression.substring(startIndex,currentIndex),startIndex);
        return lastToken;
    }

    private Token readOperator() {
        lastToken = Token.operator(expression.substring(startIndex,++currentIndex), startIndex);
        return lastToken;
    }

    private Token getReservedDelimiter(char c) {
        currentIndex++;
        lastToken = RESERVED_TOKENS.get(c).apply(startIndex);
        return lastToken;
    }

    private ExpressionParseException invalidChar(int index){
        return new ExpressionParseException(expression,currentIndex,"Invalid symbol at index %s",index+1);
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
                    "Invalid constant name, constants must have only uppercase letters");
        }

        lastToken = Token.constant(expression.substring(startIndex,currentIndex),startIndex);
        return lastToken;
    }

    private boolean isDelimiter(char c) {
        return c == ' ' || RESERVED_TOKENS.containsKey(c) || operatorChecker.isOperator(c);
    }

    private boolean isConstEligible(char c) {
        return ('A' <= c && c <= 'Z')
                || (startIndex < currentIndex && (c == '_' || isDigit(c))
        );
    }

    private boolean isValidIdentifier(char c, int startIndex) {
        return ('a' <= c && c <= 'z')
                || (startIndex < currentIndex && (c == '_' || isDigit(c))
        );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExpressionTokenizer{");
        sb.append("expression='").append(expression).append('\'');
        sb.append('}');
        return sb.toString();
    }

    private static boolean isDigit(char c){
        return Character.isDigit(c) || c == '.';
    }

    public static ExpressionTokenizer of(String expression, OperatorChecker operatorChecker){
        return new ExpressionTokenizer(expression, operatorChecker);
    }

    public interface OperatorChecker {
        boolean isOperator(char operator);
    }
}
