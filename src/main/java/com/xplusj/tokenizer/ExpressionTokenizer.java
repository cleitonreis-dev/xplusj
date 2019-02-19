package com.xplusj.tokenizer;

import com.xplusj.Environment;

import java.util.HashMap;
import java.util.Map;

public class ExpressionTokenizer {
    private static final Map<Character, Token> RESERVED_TOKENS = new HashMap<>();
    static {
        RESERVED_TOKENS.put('(', Token.parenthesisOpening());
        RESERVED_TOKENS.put(')', Token.parenthesisClosing());
        RESERVED_TOKENS.put(',', Token.getFunctionParamDelimiter());
    }

    private final String expression;
    private final int expressionLength;
    private final Environment environment;
    private int currentIndex;
    private Token lastToken;

    public ExpressionTokenizer(final String expression, final Environment environment) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.environment = environment;
    }

    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

    //TODO improve algorithm
    public Token next(){
        if(currentIndex >= expressionLength)
            return Token.EOE();

        int startIndex = currentIndex;

        while(currentIndex < expressionLength && (
                Character.isDigit(expression.charAt(currentIndex))
                        || expression.charAt(currentIndex) == '.')){
            ++currentIndex;
        }

        if(startIndex < currentIndex)
            return setLastToken(Token.number(expression.substring(startIndex, currentIndex)));

        char c = expression.charAt(currentIndex++);

        if(RESERVED_TOKENS.containsKey(c))
            return setLastToken(RESERVED_TOKENS.get(c));

        boolean maybeUnary = lastToken == null
                || lastToken.type == TokenType.PARENTHESIS_OPENING
                || lastToken.type == TokenType.BINARY_OPERATOR
                || lastToken.type == TokenType.UNARY_OPERATOR
                || lastToken.type == TokenType.FUNCTION_PARAM_DELIMITER;

        if(!maybeUnary && environment.hasBinaryOperator(c))
            return setLastToken(Token.binaryOperator(expression.substring(startIndex, currentIndex)));

        if(maybeUnary && environment.hasUnaryOperator(c))
            return setLastToken(setLastToken(Token.unaryOperator(expression.substring(startIndex, currentIndex))));

        while(currentIndex < expressionLength && (('a'<= c && c <= 'z') || ('A'<= c && c <= 'Z'))){
            if(currentIndex + 1 == expressionLength)//if needed when expression ends with constant
                c = expression.charAt(currentIndex++);
            else
                c = expression.charAt(++currentIndex);
        }

        if(startIndex < currentIndex){
            String value = expression.substring(startIndex, currentIndex);
            if(environment.hasFunction(value))
                return setLastToken(Token.function(value));

            if(environment.hasConstant(value))
                return setLastToken(Token.constant(value));
        }

        return setLastToken(Token.variable(expression.substring(startIndex, currentIndex)));
    }

    private Token setLastToken(Token token){
        lastToken = token;
        return token;
    }
}
