package com.xplusj.context;

import com.xplusj.ExpressionGlobalContext;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.factory.*;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultExpressionGlobalContextTest {

    @Mock
    private ExpressionOperatorDefinitions definitions;

    @Mock
    private ExpressionFactory expressionFactory;

    @Mock
    private ExpressionParserFactory parserFactory;

    @Mock
    private ExpressionTokenizerFactory tokenizerFactory;

    @Mock
    private ExpressionUnaryOperatorFactory unaryFactory;

    @Mock
    private ExpressionBinaryOperatorFactory binaryFactory;

    @Mock
    private ExpressionFunctionOperatorFactory functionFactory;

    @Mock
    private ExpressionParser parser;

    @Mock
    private ExpressionTokenizer tokenizer;

    @Mock
    private ExpressionOperatorDefinitions newDefinitions;

    @Before
    public void setUp(){
        when(tokenizerFactory.create(definitions)).thenReturn(tokenizer);
        when(tokenizerFactory.create(newDefinitions)).thenReturn(tokenizer);

        when(parserFactory.create(tokenizer, definitions)).thenReturn(parser);
        when(parserFactory.create(tokenizer, newDefinitions)).thenReturn(parser);

        when(definitions.append(newDefinitions)).thenReturn(newDefinitions);
    }

    @Test
    public void testCreateExpression(){
        String exp = "1+1";
        ExpressionGlobalContext context = build();
        context.expression(exp);

        verify(expressionFactory).expression(exp, context);
    }

    @Test
    public void testCreateFormula(){
        String exp = "1+1";
        ExpressionGlobalContext context = build();
        context.formula(exp);

        verify(expressionFactory).formula(exp, context);
    }

    @Test
    public void testDefinitions(){
        assertEquals("Should return same definitions object",
                definitions, build().getDefinitions());
    }

    @Test
    public void testCreateUnaryOperator(){
        char op = '+';
        UnaryOperatorDefinition opDef = UnaryOperatorDefinition.create(op, Precedence.low(),null);
        when(definitions.getUnaryOperator(op)).thenReturn(opDef);

        ExpressionGlobalContext context = build();
        context.getUnaryOperator(op);

        verify(definitions).getUnaryOperator(op);
        verify(unaryFactory).create(opDef, context);
    }

    @Test
    public void testCreateBinaryOperator(){
        char op = '+';
        BinaryOperatorDefinition opDef = BinaryOperatorDefinition.create(op, Precedence.low(),null);
        when(definitions.getBinaryOperator(op)).thenReturn(opDef);

        ExpressionGlobalContext context = build();
        context.getBinaryOperator(op);

        verify(definitions).getBinaryOperator(op);
        verify(binaryFactory).create(opDef, context);
    }

    @Test
    public void testCreateFunctionOperator(){
        String op = "sum";
        FunctionOperatorDefinition opDef = FunctionOperatorDefinition.create(op+"(a,b)",null);
        when(definitions.getFunction(op)).thenReturn(opDef);

        ExpressionGlobalContext context = build();
        context.getFunction(op);

        verify(definitions).getFunction(op);
        verify(functionFactory).create(opDef, context);
    }

    @Test
    public void testCreateTokenizer(){
        ExpressionGlobalContext context = build();
        ExpressionTokenizer tokenizer = context.getTokenizer();

        verify(tokenizerFactory).create(definitions);
        assertEquals(this.tokenizer, tokenizer);
    }

    @Test
    public void testCreateParser(){
        ExpressionGlobalContext context = build();
        ExpressionParser parser = context.getParser();

        verify(parserFactory).create(tokenizer, definitions);
        assertEquals(this.parser, parser);
    }

    @Test
    public void testAppendDefinition(){
        ExpressionGlobalContext context = build();
        ExpressionGlobalContext context2 = context.append(newDefinitions);

        assertNotEquals(context, context2);
        assertEquals(definitions, context.getDefinitions());
        assertEquals(newDefinitions, context2.getDefinitions());
    }

    @Test
    public void testAppendCreateExpression(){
        String exp = "1+1";
        ExpressionGlobalContext context = build().append(newDefinitions);
        context.expression(exp);

        verify(expressionFactory).expression(exp, context);
    }

    @Test
    public void testAppendCreateFormula(){
        String exp = "1+1";
        ExpressionGlobalContext context = build().append(newDefinitions);
        context.formula(exp);

        verify(expressionFactory).formula(exp, context);
    }

    @Test
    public void testAppendCreateUnaryOperator(){
        char op = '+';
        UnaryOperatorDefinition opDef = UnaryOperatorDefinition.create(op, Precedence.low(),null);
        when(newDefinitions.getUnaryOperator(op)).thenReturn(opDef);

        ExpressionGlobalContext context = build().append(newDefinitions);
        context.getUnaryOperator(op);

        verify(newDefinitions).getUnaryOperator(op);
        verify(unaryFactory).create(opDef, context);
    }

    @Test
    public void testAppendCreateBinaryOperator(){
        char op = '+';
        BinaryOperatorDefinition opDef = BinaryOperatorDefinition.create(op, Precedence.low(),null);
        when(newDefinitions.getBinaryOperator(op)).thenReturn(opDef);

        ExpressionGlobalContext context = build().append(newDefinitions);
        context.getBinaryOperator(op);

        verify(newDefinitions).getBinaryOperator(op);
        verify(binaryFactory).create(opDef, context);
    }

    @Test
    public void testAppendCreateFunctionOperator(){
        String op = "sum";
        FunctionOperatorDefinition opDef = FunctionOperatorDefinition.create(op+"(a,b)",null);
        when(newDefinitions.getFunction(op)).thenReturn(opDef);

        ExpressionGlobalContext context = build().append(newDefinitions);
        context.getFunction(op);

        verify(newDefinitions).getFunction(op);
        verify(functionFactory).create(opDef, context);
    }

    @Test
    public void testAppendCreateTokenizer(){
        ExpressionGlobalContext context = build().append(newDefinitions);
        ExpressionTokenizer tokenizer = context.getTokenizer();

        verify(tokenizerFactory).create(newDefinitions);
        assertEquals(this.tokenizer, tokenizer);
    }

    @Test
    public void testAppendCreateParser(){
        ExpressionGlobalContext context = build().append(newDefinitions);
        ExpressionParser parser = context.getParser();

        verify(parserFactory).create(tokenizer, newDefinitions);
        assertEquals(this.parser, parser);
    }

    private ExpressionGlobalContext build(){
        return DefaultExpressionGlobalContext.builder()
                .setOperatorDefinitions(definitions)
                .setExpressionFactory(expressionFactory)
                .setParserFactory(parserFactory)
                .setTokenizerFactory(tokenizerFactory)
                .setUnaryOperatorFactory(unaryFactory)
                .setBinaryOperatorFactory(binaryFactory)
                .setFunctionOperatorFactory(functionFactory)
                .build();
    }
}