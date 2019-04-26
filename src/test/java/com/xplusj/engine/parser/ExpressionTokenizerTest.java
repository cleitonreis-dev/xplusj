package com.xplusj.engine.parser;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ExpressionTokenizerTest {

    private static Set<Character> operators = new HashSet<>(Arrays.asList(
       '+','-','*','/'
    ));

    private static ExpressionTokenizer.ExistingOperator existingOperator = c->operators.contains(c);

    @Test
    public void shouldFindNumber(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("1",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindDottedNumber(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("1",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol1(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("+",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.OPERATOR, token.type);
        assertEquals("+", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol2(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("func_name",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.SYMBOL, token.type);
        assertEquals("func_name", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisOpening(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("(",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_OPENING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisClosing(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(")",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_CLOSING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindComma(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(",",existingOperator);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.COMMA, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldReturnTokens(){
        List<Token> expectedTokens = Arrays.asList(
            Token.symbol("a",0),
            Token.operator("+",1),
            Token.symbol("bc",2),
            Token.operator("-",4),
            Token.number("1.9",5),
            Token.operator("+",8),
            Token.symbol("abc",9),
            Token.parenthesisOpening(12),
            Token.number("1",13),
            Token.comma(14),
            Token.symbol("b",15),
            Token.parenthesisClosing(16),
            Token.EOE()
        );

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("a+bc-1.9+abc(1,b)",existingOperator);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++){
            assertEquals(expectedTokens.get(i), tokens.get(i));
        }
    }
}