package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.parser.ExpressionParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ExpressionOperatorDefinitions operatorDefinitions;

    @Before
    public void setUp(){
        when(operatorDefinitions.hasBinaryOperator("==")).thenReturn(true);
        when(operatorDefinitions.hasBinaryOperator(">")).thenReturn(true);
        when(operatorDefinitions.hasBinaryOperator(">=")).thenReturn(true);
    }

    @Test
    public void shouldFindNumber(){
        Tokenizer tokenizer = new Tokenizer("1", operatorDefinitions);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindDottedNumber(){
        Tokenizer tokenizer = new Tokenizer("1", operatorDefinitions);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals("1", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol1(){
        Tokenizer tokenizer = new Tokenizer("+", operatorDefinitions);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.OPERATOR, token.type);
        assertEquals("+", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindSymbol2(){
        Tokenizer tokenizer = new Tokenizer("func_name", operatorDefinitions);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.VAR, token.type);
        assertEquals("func_name", token.value);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisOpening(){
        Tokenizer tokenizer = new Tokenizer("(", operatorDefinitions);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_OPENING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindParenthesisClosing(){
        Tokenizer tokenizer = new Tokenizer(")", operatorDefinitions);
        assertTrue(tokenizer.hasNext());
        Token token = tokenizer.next();
        assertEquals(TokenType.PARENTHESIS_CLOSING, token.type);
        assertEquals(0, token.index);
    }

    @Test
    public void shouldFindComma(){
        Tokenizer tokenizer = new Tokenizer(",", operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer("CON", operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer("CON+AAA_B", operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer("CONa+AAA_B", operatorDefinitions);

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

        Tokenizer tokenizer = new Tokenizer("CON+aaa_b", operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer("CONa+aaa", operatorDefinitions);

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

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
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

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void shouldIgnoreSpaces6(){
        String expression = " 1 * c1 -  func12( b3 + 1.1 ) / 4.2 ";
        List<Token> expectedTokens = Arrays.asList(
                Token.number("1",1),
                Token.operator("*",3),
                Token.var("c1",5),
                Token.operator("-",8),
                Token.func("func12", 11),
                Token.parenthesisOpening(17),
                Token.var("b3",19),
                Token.operator("+",22),
                Token.number("1.1",24),
                Token.parenthesisClosing(28),
                Token.operator("/",30),
                Token.number("4.2",32),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(tokenizer.getExpression(), expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void testToString(){
        String expression = "1+2-1";
        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);

        assertEquals(
            format("%s{expression='%s'}", Tokenizer.class.getSimpleName(), expression),
            tokenizer.toString()
        );
    }

    @Test @Ignore
    public void shouldThrowExceptionForInvalidIdentifier(){
        String expression = "1+abc|d";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(expression,5,"Binary operator '%s' not found","|").getMessage());

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
        while (tokenizer.hasNext())
            tokenizer.next();
    }

    @Test
    public void shouldThrowExceptionForInvalidIdentifier2(){
        String expression = "1+abc .";

        thrown.expect(ExpressionParseException.class);
        thrown.expectMessage(new ExpressionParseException(expression,6,"Invalid symbol at index %s", 7).getMessage());

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
        while (tokenizer.hasNext())
            tokenizer.next();
    }

    @Test
    public void testTokenToString(){
        String expression = "1+a*2-func(AB,v)";

        List<String> expectedStrings = Arrays.asList(
            "Token(type=NUMBER, value=1, index=0)",
            "Token(type=OPERATOR, value=+, index=1)",
            "Token(type=VAR, value=a, index=2)",
            "Token(type=OPERATOR, value=*, index=3)",
            "Token(type=NUMBER, value=2, index=4)",
            "Token(type=OPERATOR, value=-, index=5)",
            "Token(type=FUNC, value=func, index=6)",
            "Token(type=PARENTHESIS_OPENING, value=(, index=10)",
            "Token(type=CONST, value=AB, index=11)",
            "Token(type=COMMA, value=,, index=13)",
            "Token(type=VAR, value=v, index=14)",
            "Token(type=PARENTHESIS_CLOSING, value=), index=15)",
            "Token(type=EOE, value=, index=16)"
        );

        Tokenizer tokenizer = new Tokenizer(expression, operatorDefinitions);
        int i = 0;
        while (tokenizer.hasNext()) {
            Token token = tokenizer.next();
            assertEquals(expectedStrings.get(i++), token.toString());
        }
    }

    @Test
    public void testOperatorLongIdentifier(){
        List<Token> expectedTokens = Arrays.asList(
                Token.number("1",0),
                Token.operator("==",1),
                Token.number("2",3),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer("1==2", operatorDefinitions);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void testOperatorLongIdentifier2(){
        List<Token> expectedTokens = Arrays.asList(
                Token.number("1",0),
                Token.operator(">",1),
                Token.number("2",2),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer("1>2", operatorDefinitions);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(expectedTokens.get(i), tokens.get(i));
    }

    @Test
    public void testOperatorLongIdentifier3(){
        List<Token> expectedTokens = Arrays.asList(
                Token.number("1",0),
                Token.operator(">=",1),
                Token.number("2",3),
                Token.EOE()
        );

        Tokenizer tokenizer = new Tokenizer("1>=2", operatorDefinitions);
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.hasNext())
            tokens.add(tokenizer.next());

        tokens.add(tokenizer.next());

        for(int i = 0; i < expectedTokens.size(); i++)
            assertEquals(expectedTokens.get(i), tokens.get(i));
    }
}