package com.xplusj.tokenizer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Token {
    private static final Token EOE = new Token(TokenType.EOE, null);
    private static final Token PARENTHESIS_OPENING = new Token(TokenType.PARENTHESIS_OPENING, null);
    private static final Token PARENTHESIS_CLOSING = new Token(TokenType.PARENTHESIS_CLOSING, null);
    private static final Token FUNCTION_PARAM_DELIMITER = new Token(TokenType.FUNCTION_PARAM_DELIMITER, null);

    public final TokenType type;
    public final String value;


    public static Token number(String value){
        return new Token(TokenType.NUMBER, value);
    }

    public static Token variable(String value){
        return new Token(TokenType.VARIABLE, value);
    }

    public static Token function(String value){
        return new Token(TokenType.FUNCTION, value);
    }

    public static Token binaryOperator(String value){
        return new Token(TokenType.BINARY_OPERATOR, value);
    }

    public static Token unaryOperator(String value){
        return new Token(TokenType.UNARY_OPERATOR, value);
    }

    public static Token parenthesisOpening(){
        return PARENTHESIS_OPENING;
    }

    public static Token parenthesisClosing(){
        return PARENTHESIS_CLOSING;
    }

    public static Token getFunctionParamDelimiter(){
        return FUNCTION_PARAM_DELIMITER;
    }

    public static Token EOE(){
        return EOE;
    }
}
