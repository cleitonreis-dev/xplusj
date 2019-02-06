package com.xplusj.tokenizer;

import com.xplusj.Environment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionTokenizerTests {

    @Mock
    private Environment environment;

    @Test
    public void shouldReturnSingleDigit(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("2", environment);

        while (tokenizer.hasNext()){
            assertEquals(Token.number("2"), tokenizer.next());
        }
    }

    @Test
    public void shouldReturnMultipleDigits(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("2222", environment);

        while (tokenizer.hasNext()){
            assertEquals(Token.number("2222"), tokenizer.next());
        }
    }

    @Test
    public void shouldReturnParenthesisAndDigit(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("(2)", environment);
        List<Token> tokenList = new ArrayList<>();

        while (tokenizer.hasNext()){
            tokenList.add(tokenizer.next());
        }

        assertEquals(Token.parenthesisOpening(), tokenList.get(0));
        assertEquals(Token.number("2"), tokenList.get(1));
        assertEquals(Token.parenthesisClosing(), tokenList.get(2));
    }

    @Test
    public void shouldReturnParenthesisAndDigits(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("(22)", environment);
        List<Token> tokenList = new ArrayList<>();

        while (tokenizer.hasNext()){
            tokenList.add(tokenizer.next());
        }

        assertEquals(Token.parenthesisOpening(), tokenList.get(0));
        assertEquals(Token.number("22"), tokenList.get(1));
        assertEquals(Token.parenthesisClosing(), tokenList.get(2));
    }

    @Test
    public void shouldReturnOperator(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("2+3", environment);
        List<Token> tokenList = new ArrayList<>();

        when(environment.hasOperator('+')).thenReturn(true);

        while (tokenizer.hasNext()){
            tokenList.add(tokenizer.next());
        }

        assertEquals(Token.number("2"), tokenList.get(0));
        assertEquals(Token.operator("+"), tokenList.get(1));
        assertEquals(Token.number("3"), tokenList.get(2));
    }

}