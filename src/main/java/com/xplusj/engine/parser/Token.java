package com.xplusj.engine.parser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Token {
    private static final Token EOE = new Token(TokenType.EOE, null,-1);

    final TokenType type;
    final String value;
    final int index;

    public static Token number(String value, int index){
        return new Token(TokenType.NUMBER, value, index);
    }

    public static Token constant(String value, int index){
        return new Token(TokenType.CONST, value, index);
    }

    public static Token operator(String value, int index){
        return new Token(TokenType.OPERATOR, value, index);
    }

    public static Token parenthesisOpening(int index){
        return new Token(TokenType.PARENTHESIS_OPENING, null, index);
    }

    public static Token parenthesisClosing(int index){
        return new Token(TokenType.PARENTHESIS_CLOSING, null, index);
    }

    public static Token comma(int index){
        return new Token(TokenType.COMMA, null, index);
    }

    public static Token EOE(){
        return EOE;
    }

    public static Token var(String value, int index) {
        return new Token(TokenType.VAR, value, index);
    }

    public static Token func(String name, int index) {
        return new Token(TokenType.FUNC, name, index);
    }
}