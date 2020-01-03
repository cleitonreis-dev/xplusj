package com.xplusj.context;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperators;
import com.xplusj.factory.*;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.function.FunctionOperatorContext;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.parser.ExpressionParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultExpressionContextTest {

    @Mock
    private ExpressionOperators definitions;

    @Mock
    private ExpressionFactory expressionFactory;

    @Mock
    private ExpressionParserFactory parserFactory;

    @Mock
    private ExpressionUnaryOperatorFactory unaryFactory;

    @Mock
    private ExpressionBinaryOperatorFactory binaryFactory;

    @Mock
    private ExpressionFunctionOperatorFactory functionFactory;

    @Mock
    private ExpressionParser parser;

    @Mock
    private ExpressionOperators newDefinitions;

    @Before
    public void setUp(){
        when(parserFactory.create(any(ExpressionContext.class))).thenReturn(parser);
        //when(parserFactory.create(tokenizer, newDefinitions)).thenReturn(parser);

        when(definitions.append(newDefinitions)).thenReturn(newDefinitions);
    }

    @Test
    public void testCreateExpression(){
        String exp = "1+1";
        ExpressionContext context = build();
        context.expression(exp);

        verify(expressionFactory).expression(exp, context);
    }

    @Test
    public void testCreateFormula(){
        String exp = "1+1";
        ExpressionContext context = build();
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
        String op = "+";
        UnaryOperator opDef = UnaryOperator.unary(op, Precedence.low(),null);
        when(definitions.getUnaryOperator(op)).thenReturn(opDef);

        ExpressionContext context = build();
        context.getUnaryOperator(op);

        verify(definitions).getUnaryOperator(op);
        verify(unaryFactory).create(opDef, context);
    }

    @Test
    public void testCreateBinaryOperator(){
        String op = "+";
        BinaryOperator opDef = BinaryOperator.binary(op, Precedence.low(),null);
        when(definitions.getBinaryOperator(op)).thenReturn(opDef);

        ExpressionContext context = build();
        context.getBinaryOperator(op);

        verify(definitions).getBinaryOperator(op);
        verify(binaryFactory).create(opDef, context);
    }

    @Test
    public void testCreateFunctionOperator(){
        String op = "sum";
        FunctionOperator opDef = FunctionOperator.func(op+"(a,b)",(Function<FunctionOperatorContext, Double>) null);
        when(definitions.getFunction(op)).thenReturn(opDef);

        ExpressionContext context = build();
        context.getFunction(op);

        verify(definitions).getFunction(op);
        verify(functionFactory).create(opDef, context);
    }

    @Test
    public void testCreateParser(){
        ExpressionContext context = build();
        ExpressionParser parser = context.getParser();

        verify(parserFactory).create(context);
        assertEquals(this.parser, parser);
    }

    @Test
    public void testAppendDefinition(){
        ExpressionContext context = build();
        ExpressionContext context2 = context.append(newDefinitions);

        assertNotEquals(context, context2);
        assertEquals(definitions, context.getDefinitions());
        assertEquals(newDefinitions, context2.getDefinitions());
    }

    @Test
    public void testAppendCreateExpression(){
        String exp = "1+1";
        ExpressionContext context = build().append(newDefinitions);
        context.expression(exp);

        verify(expressionFactory).expression(exp, context);
    }

    @Test
    public void testAppendCreateFormula(){
        String exp = "1+1";
        ExpressionContext context = build().append(newDefinitions);
        context.formula(exp);

        verify(expressionFactory).formula(exp, context);
    }

    @Test
    public void testAppendCreateUnaryOperator(){
        String op = "+";
        UnaryOperator opDef = UnaryOperator.unary(op, Precedence.low(),null);
        when(newDefinitions.getUnaryOperator(op)).thenReturn(opDef);

        ExpressionContext context = build().append(newDefinitions);
        context.getUnaryOperator(op);

        verify(newDefinitions).getUnaryOperator(op);
        verify(unaryFactory).create(opDef, context);
    }

    @Test
    public void testAppendCreateBinaryOperator(){
        String op = "+";
        BinaryOperator opDef = BinaryOperator.binary(op, Precedence.low(),null);
        when(newDefinitions.getBinaryOperator(op)).thenReturn(opDef);

        ExpressionContext context = build().append(newDefinitions);
        context.getBinaryOperator(op);

        verify(newDefinitions).getBinaryOperator(op);
        verify(binaryFactory).create(opDef, context);
    }

    @Test
    public void testAppendCreateFunctionOperator(){
        String op = "sum";
        FunctionOperator opDef = FunctionOperator.func(op+"(a,b)",(Function<FunctionOperatorContext, Double>)null);
        when(newDefinitions.getFunction(op)).thenReturn(opDef);

        ExpressionContext context = build().append(newDefinitions);
        context.getFunction(op);

        verify(newDefinitions).getFunction(op);
        verify(functionFactory).create(opDef, context);
    }

    @Test
    public void testAppendCreateParser(){
        ExpressionContext context = build().append(newDefinitions);
        ExpressionParser parser = context.getParser();

        verify(parserFactory).create(context);
        assertEquals(this.parser, parser);
    }

    private ExpressionContext build(){
        return DefaultExpressionContext.builder()
                .setOperatorDefinitions(definitions)
                .setExpressionFactory(expressionFactory)
                .setParserFactory(parserFactory)
                .setUnaryOperatorFactory(unaryFactory)
                .setBinaryOperatorFactory(binaryFactory)
                .setFunctionOperatorFactory(functionFactory)
                .build();
    }
}