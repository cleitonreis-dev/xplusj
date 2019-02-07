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
    public void shouldReturnDigitsWithDots(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("2222.3333", environment);

        while (tokenizer.hasNext()){
            assertEquals(Token.number("2222.3333"), tokenizer.next());
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

    @Test
    public void shouldReturnFunctionWithSingleParam(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("2+fsp(3)", environment);
        List<Token> tokenList = new ArrayList<>();

        when(environment.hasOperator('+')).thenReturn(true);
        when(environment.hasFunction("fsp")).thenReturn(true);

        while (tokenizer.hasNext()){
            tokenList.add(tokenizer.next());
        }

        assertEquals(Token.number("2"), tokenList.get(0));
        assertEquals(Token.operator("+"), tokenList.get(1));
        assertEquals(Token.function("fsp"), tokenList.get(2));
        assertEquals(Token.parenthesisOpening(), tokenList.get(3));
        assertEquals(Token.number("3"), tokenList.get(4));
        assertEquals(Token.parenthesisClosing(), tokenList.get(5));
    }

    @Test
    public void shouldReturnFunctionWithMultipleParams(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("2+fmp(3,2,4)", environment);
        List<Token> tokenList = new ArrayList<>();

        when(environment.hasOperator('+')).thenReturn(true);
        when(environment.hasFunction("fmp")).thenReturn(true);

        while (tokenizer.hasNext()){
            tokenList.add(tokenizer.next());
        }

        assertEquals(Token.number("2"), tokenList.get(0));
        assertEquals(Token.operator("+"), tokenList.get(1));
        assertEquals(Token.function("fmp"), tokenList.get(2));
        assertEquals(Token.parenthesisOpening(), tokenList.get(3));
        assertEquals(Token.number("3"), tokenList.get(4));
        assertEquals(Token.getFunctionParamDelimiter(), tokenList.get(5));
        assertEquals(Token.number("2"), tokenList.get(6));
        assertEquals(Token.getFunctionParamDelimiter(), tokenList.get(7));
        assertEquals(Token.number("4"), tokenList.get(8));
        assertEquals(Token.parenthesisClosing(), tokenList.get(9));
    }

}