package com.xplusj.tokenizer;

import com.xplusj.Environment;

import java.util.Collections;
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
    private final Map<String,Double> vars;
    private int currentIndex;
    private Token lastToken;

    public ExpressionTokenizer(final String expression, final Environment environment, final Map<String,Double> vars) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.environment = environment;
        this.vars = vars;
    }

    public ExpressionTokenizer(final String expression, final Environment environment) {
        this(expression,environment,Collections.emptyMap());
    }

    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

    //TODO improve algorithm
    public Token next(){
        if(currentIndex == expressionLength)
            return Token.EOE();

        int startIndex = currentIndex;
        char c = expression.charAt(currentIndex);

        while((Character.isDigit(c) || c == '.') && currentIndex < expressionLength){
            if((currentIndex + 1) < expressionLength)
                c = expression.charAt(++currentIndex);
            else
                ++currentIndex;
        }

        if(startIndex < currentIndex)
            return setLastToken(Token.number(expression.substring(startIndex, currentIndex)));

        if(RESERVED_TOKENS.containsKey(c)) {
            ++currentIndex;
            return setLastToken(RESERVED_TOKENS.get(c));
        }

        boolean unaryEligible = lastToken == null
                || lastToken.type == TokenType.PARENTHESIS_OPENING
                || lastToken.type == TokenType.BINARY_OPERATOR
                || lastToken.type == TokenType.UNARY_OPERATOR
                || lastToken.type == TokenType.FUNCTION_PARAM_DELIMITER;

        if(!unaryEligible && environment.hasBinaryOperator(c))
            return setLastToken(Token.binaryOperator(expression.substring(startIndex, ++currentIndex)));

        if(unaryEligible && environment.hasUnaryOperator(c))
            return setLastToken(setLastToken(Token.unaryOperator(expression.substring(startIndex, ++currentIndex))));

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
