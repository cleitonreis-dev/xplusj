package com.xplusj.interpreter.parser;

import com.xplusj.parser.ExpressionParseException;
import com.xplusj.tokenizer.Token;
import com.xplusj.tokenizer.Tokenizer;
import com.xplusj.tokenizer.TokenType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionTokenizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Set<Character> operators = new HashSet<>(Arrays.asList(
       '+','-','*','/'
    ));

    private static Tokenizer.OperatorChecker operatorChecker = c->operators.contains(c);

    @Test
    public void shouldFindNumber(){
        Tokenizer tokenizer = new Tokenizer("1", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindDottedNumber(){
        Tokenizer tokenizer = new Tokenizer("1", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol1(){
        Tokenizer tokenizer = new Tokenizer("+", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.OPERATOR, token.type);
        assertEquals("+", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol2(){
        Tokenizer tokenizer = new Tokenizer("func_name", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.VAR, token.type);
        assertEquals("func_name", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisOpening(){
        Tokenizer tokenizer = new Tokenizer("(", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_OPENING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisClosing(){
        Tokenizer tokenizer = new Tokenizer(")", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_CLOSING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindComma(){
        Tokenizer tokenizer = new Tokenizer(",", operatorChecker);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.COMMA, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldReturnTokens(){
        String expression = "a+bc-1.9+abc(1,b)";

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

        Tokenizer tokenizer = new Tokenizer(expression, operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++){
            assertEquals(expression,expectedTokens.get(i), tokens.get(i));
        }
    }

    @Test
    public void shouldParseConstant(){
        List<Token> expectedTokens = Arrays.asList(
                Token.constant("CON",0),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer("CON", operatorChecker);
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

        Tokenizer tokenizer = new Tokenizer("CON+AAA_B", operatorChecker);
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

        Tokenizer tokenizer = new Tokenizer("CONa+AAA_B", operatorChecker);

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

        Tokenizer tokenizer = new Tokenizer("CON+aaa_b", operatorChecker);
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

        Tokenizer tokenizer = new Tokenizer("CONa+aaa", operatorChecker);

        while (tokenizer.hasNext())
            tokenizer.next();
    }

    @Test
    public void shouldIgnoreSpaces1(){
        String expression = "  1  ";
        List<Token> expectedTokens = Arrays.asList(
                Token.number("1",2),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void shouldIgnoreSpaces2(){
        String expression = "  b  ";
        List<Token> expectedTokens = Arrays.asList(
                Token.var("b",2),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void shouldIgnoreSpaces3(){
        String expression = "  func( 1 )  ";
        List<Token> expectedTokens = Arrays.asList(
                Token.func("func",2),
                Token.parenthesisOpening(6),
                Token.number("1",8),
                Token.parenthesisClosing(10),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void shouldIgnoreSpaces4(){
        String expression = "  func( b )  ";
        List<Token> expectedTokens = Arrays.asList(
                Token.func("func",2),
                Token.parenthesisOpening(6),
                Token.var("b",8),
                Token.parenthesisClosing(10),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void shouldIgnoreSpaces5(){
        String expression = " 1 * c -  func( b + 1.1 ) / 4.2 ";
        List<Token> expectedTokens = Arrays.asList(
                Token.number("1",1),
                Token.operator("*",3),
                Token.var("c",5),
                Token.operator("-",7),
                Token.func("func", 10),
                Token.parenthesisOpening(14),
                Token.var("b",16),
                Token.operator("+",18),
                Token.number("1.1",20),
                Token.parenthesisClosing(24),
                Token.operator("/",26),
                Token.number("4.2",28),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorChecker);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }
}