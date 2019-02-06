package com.xplusj.tokenizer;

import java.util.Objects;

public class Token {
    private static final Token EOE = new Token(TokenType.EOE, null);
    private static final Token PARENTHESIS_OPENING = new Token(TokenType.PARENTHESIS_OPENING, null);
    private static final Token PARENTHESIS_CLOSING = new Token(TokenType.PARENTHESIS_CLOSING, null);

    public final TokenType type;
    public final String value;

    private Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Token number(String value){
        return new Token(TokenType.NUMBER, value);
    }

    public static Token variable(String value){
        return new Token(TokenType.VARIABLE, value);
    }

    public static Token function(String value){
        return new Token(TokenType.FUNCTION, value);
    }

    public static Token operator(String value){
        return new Token(TokenType.OPERATOR, value);
    }

    public static Token parenthesisOpening(){
        return PARENTHESIS_OPENING;
    }

    public static Token parenthesisClosing(){
        return PARENTHESIS_CLOSING;
    }

    public static Token EOE(){
        return EOE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return type == token.type &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token{");
        sb.append("type=").append(type);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
