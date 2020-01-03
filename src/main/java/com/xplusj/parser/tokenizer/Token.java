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

import java.util.Objects;

public class Token {
    private static final Token EOE = new Token(TokenType.EOE, null,-1);

    public final TokenType type;
    public final String value;
    public final int index;

    protected Token(TokenType type, String value, int index) {
        this.type = type;
        this.value = value;
        this.index = index;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return index == token.index &&
                type == token.type &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, index);
    }

    @Override
    public String toString() {
        return "Token(" +
                "type=" + type +
                ", value=" + value +
                ", index=" + index +
                ')';
    }

    public static Token number(String value, int index){
        return new Token(TokenType.NUMBER, value, index);
    }

    public static Token constant(String value, int index){
        return new Token(TokenType.CONST, value, index);
    }

    public static Token operator(String value, int index){
        return new Token(TokenType.OPERATOR, value, index);
    }

    public static Token var(String value, int index){
        return new Token(TokenType.VAR, value, index);
    }
    public static Token func(String value, int index){
        return new Token(TokenType.FUNC, value, index);
    }

    public static Token parenthesisOpening(int index){
        return new Token(TokenType.PARENTHESIS_OPENING, "(", index);
    }

    public static Token parenthesisClosing(int index){
        return new Token(TokenType.PARENTHESIS_CLOSING, ")", index);
    }

    public static Token comma(int index){
        return new Token(TokenType.COMMA, ",", index);
    }

    public static Token EOE(){
        return EOE;
    }

    /*public static Token identifier(String value, int index) {
        return new Token(TokenType.IDENTIFIER, value, index);
    }*/
}
