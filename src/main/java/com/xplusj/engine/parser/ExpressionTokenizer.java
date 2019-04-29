package com.xplusj.engine.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

public class ExpressionTokenizer {
    private static final Map<Character, Function<Integer, Token>> RESERVED_TOKENS = new HashMap<>();
    static {
        RESERVED_TOKENS.put('(', Token::parenthesisOpening);
        RESERVED_TOKENS.put(')', Token::parenthesisClosing);
        RESERVED_TOKENS.put(',', Token::comma);
    }

    private final String expression;
    private final int expressionLength;
    private final OperatorChecker operatorChecker;
    private int currentIndex;

    public ExpressionTokenizer(final String expression, final OperatorChecker operatorChecker) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.operatorChecker = operatorChecker;
    }

    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

    //TODO improve algorithm and tokens definition
    public Token next(){
        if(currentIndex >= expressionLength)
            return Token.EOE();

        int startIndex = currentIndex;
        char c = nextValidChar();

        if(isDigit(c))
            return Token.number(readNumber(startIndex), startIndex);

        if(operatorChecker.isOperator(c))
            return Token.operator(expression.substring(startIndex,++currentIndex), startIndex);

        if(RESERVED_TOKENS.containsKey(c)) {
            currentIndex++;
            return RESERVED_TOKENS.get(c).apply(startIndex);
        }

        if(isConstEligible(c,startIndex))
            return Token.constant(readConst(startIndex),startIndex);

        if(isVarOrFunctionEligible(c,startIndex))
            return getVarOrFunction(startIndex);

        throw invalidChar(currentIndex);
    }

    private String readNumber(int startIndex) {
        while(true){
            currentIndex++;

            if(!hasNext())
                break;

            char c = expression.charAt(currentIndex);
            if(!isDigit(c))
                break;
        }

        return expression.substring(startIndex,currentIndex);
    }

    private Token getVarOrFunction(int startIndex) {
        String identifier = readIdentifier(startIndex);
        if(!hasNext())
            return Token.var(identifier,startIndex);

        char c = nextValidChar();

        if(c == '(')
            return Token.func(identifier,startIndex);

        if(isDelimiter(c))
            return Token.var(identifier,startIndex);

        throw invalidChar(startIndex);
    }

    private ExpressionParseException invalidChar(int index){
        return new ExpressionParseException(format("Invalid symbol at index %s",index+1),
                expression,currentIndex);
    }

    private char nextValidChar() {
        char c = expression.charAt(currentIndex);

        while(true){
            if(c != ' ')
                break;

            currentIndex++;
            if(!hasNext())
                break;

            c = expression.charAt(currentIndex);
        }

        return c;
    }

    private String readIdentifier(int startIndex) {
        while(true){
            if(!hasNext())
                break;

            char c = expression.charAt(currentIndex);
            if(isDelimiter(c))
                break;

            if(!isVarOrFunctionEligible(c,startIndex))
                throw new ExpressionParseException(
                        "Invalid identifier, variables and functions must have only lowercase letters",
                        expression, currentIndex);
            currentIndex++;
        }

        return expression.substring(startIndex,currentIndex);
    }

    private String readConst(int startIndex) {
        while(true){
            currentIndex++;

            if(!hasNext())
                break;

            char c = expression.charAt(currentIndex);
            if(isDelimiter(c))
                break;

            if(!isConstEligible(c,startIndex))
                throw new ExpressionParseException(
                    "Invalid constant name, constants must have only capitalized letters",
                    expression, currentIndex);
        }

        return expression.substring(startIndex,currentIndex);
    }

    private boolean isDelimiter(char c) {
        return c == ' ' || RESERVED_TOKENS.containsKey(c) || operatorChecker.isOperator(c);
    }

    private boolean isConstEligible(char c, int startIndex) {
        return ('A' <= c && c <= 'Z')
                || (startIndex < currentIndex && (c == '_' || isDigit(c))
        );
    }

    private boolean isVarOrFunctionEligible(char c, int startIndex) {
        return ('a' <= c && c <= 'z')
                || (startIndex < currentIndex && (c == '_' || isDigit(c))
        );
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
