package com.xplusj.engine.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.*;

public class ExpressionTokenizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Set<Character> operators = new HashSet<>(Arrays.asList(
       '+','-','*','/'
    ));

    private static ExpressionTokenizer.OperatorChecker operatorChecker = c->operators.contains(c);

    @Test
    public void shouldFindNumber(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("1", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindDottedNumber(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("1", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol1(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("+", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.OPERATOR, token.type);
        assertEquals("+", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol2(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("func_name", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.VAR, token.type);
        assertEquals("func_name", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisOpening(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer("(", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_OPENING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisClosing(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(")", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_CLOSING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindComma(){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(",", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.COMMA, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldReturnTokens(){
        List<Token> expectedTokens = Arrays.asList(
            Token.var("a",0),
            Token.operator("+",1),
            Token.var("bc",2),
            Token.operator("-",4),
            Token.number("1.9",5),
            Token.operator("+",8),
            Token.func("abc",9),
            Token.parenthesisOpening(12),
            Token.number("1",13),
            Token.comma(14),
            Token.var("b",15),
            Token.parenthesisClosing(16),
            Token.EOE()
        );

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("a+bc-1.9+abc(1,b)", operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++){
            assertEquals(expectedTokens.get(i), tokens.get(i));
        }
    }

    @Test
    public void shouldParseConstant(){
        List<Token> expectedTokens = Arrays.asList(
                Token.constant("CON",0),
                Token.EOE()
        );

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("CON", operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++){
            assertEquals(expectedTokens.get(i), tokens.get(i));
        }
    }

    @Test
    public void shouldParseConstant2(){
        List<Token> expectedTokens = Arrays.asList(
                Token.constant("CON",0),
                Token.operator("+",3),
                Token.constant("AAA_B",4),
                Token.EOE()
        );

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("CON+AAA_B", operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++){
            assertEquals(expectedTokens.get(i), tokens.get(i));
        }
    }

    @Test
    public void shouldParseConstantInvalidIdentifier3(){
        thrown.expect(ExpressionParseException.class);

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("CONa+AAA_B", operatorChecker);

        while (tokenizer.hasNext())
            tokenizer.next();
    }

    @Test
    public void shouldParseVars(){
        List<Token> expectedTokens = Arrays.asList(
                Token.constant("CON",0),
                Token.operator("+",3),
                Token.var("aaa_b",4),
                Token.EOE()
        );

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("CON+aaa_b", operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++){
            assertEquals(expectedTokens.get(i), tokens.get(i));
        }
    }

    @Test
    public void shouldParseVarInvalidIdentifier(){
        thrown.expect(ExpressionParseException.class);

        ExpressionTokenizer tokenizer = new ExpressionTokenizer("CONa+aaaB", operatorChecker);

        while (tokenizer.hasNext())
            tokenizer.next();
    }
}