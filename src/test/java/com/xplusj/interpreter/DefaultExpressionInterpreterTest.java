package com.xplusj.interpreter;

import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.interpreter.stack.Stack;
import com.xplusj.operator.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultExpressionInterpreterTest{

    /*private static GlobalContext globalContext = DefaultGlobalContext.builder()
        .addUnaryOperator(UnaryOperator.create('+', Precedence.highest(), ctx->+ctx.getValue()))
        .addUnaryOperator(UnaryOperator.create('-', Precedence.highest(), ctx->-ctx.getValue()))
        .addBinaryOperator(BinaryOperator.create('+', Precedence.low(), ctx->ctx.getFirstValue()+ctx.getSecondValue()))
        .addBinaryOperator(BinaryOperator.create('-', Precedence.low(), ctx->ctx.getFirstValue()-ctx.getSecondValue()))
        .addBinaryOperator(BinaryOperator.create('/', Precedence.high(), ctx->ctx.getFirstValue()/ctx.getSecondValue()))
        .addBinaryOperator(BinaryOperator.create('*', Precedence.high(), ctx->ctx.getFirstValue()*ctx.getSecondValue()))
        .addConstant("PI",Math.PI)
        .addFunction(FunctionOperator.create("sum(a,b)", ctx->ctx.param("a") + ctx.param("b")))
        .build();*/

    @Mock
    private GlobalContext globalContext;

    @Mock
    private FunctionOperator functionOperator;

    @Mock
    private UnaryOperator unaryOperator;

    @Mock
    private BinaryOperator binaryOperator;

    @Mock
    private Stack<Double> valStack;

    @Mock
    private Stack<Operator<?>> opStack;

    @Mock
    private VariableContext variableContext;

    private DefaultExpressionInterpreter interpreter;

    @Before
    public void setUp(){
        interpreter = new DefaultExpressionInterpreter(globalContext, variableContext, valStack, opStack);

        when(unaryOperator.getType()).thenReturn(OperatorType.UNARY);
        when(unaryOperator.getParamsLength()).thenReturn(1);
        when(binaryOperator.getType()).thenReturn(OperatorType.BINARY);
        when(binaryOperator.getParamsLength()).thenReturn(2);
        when(functionOperator.getType()).thenReturn(OperatorType.FUNCTION);
        when(functionOperator.getParamsLength()).thenReturn(3);
    }

    @Test
    public void pushValue() {
        interpreter.pushValue(1D);
        verify(valStack).push(1D);
    }

    @Test
    public void pushVar() {
        when(variableContext.contains("a")).thenReturn(true);
        when(variableContext.value("a")).thenReturn(2D);

        interpreter.pushVar("a");

        verify(valStack).push(2D);
    }

    @Test
    public void pushConstant() {
        when(globalContext.hasConstant("AB")).thenReturn(true);
        when(globalContext.getConstant("AB")).thenReturn(3D);

        interpreter.pushConstant("AB");

        verify(valStack).push(3D);
    }

    @Test
    public void pushOperator() {
        interpreter.pushOperator(unaryOperator);
        verify(opStack).push(unaryOperator);
    }

    @Test
    public void getLastOperator() {
        interpreter.getLastOperator();
        verify(opStack).peek();
    }

    @Test
    public void testCallUnaryOperator() {
        when(unaryOperator.execute(any(UnaryOperatorContext.class))).thenReturn(3D);
        when(valStack.pull()).thenReturn(2D);
        doReturn(unaryOperator).when(opStack).pull();

        ArgumentCaptor<UnaryOperatorContext> opContextCap = ArgumentCaptor.forClass(UnaryOperatorContext.class);

        interpreter.callLastOperatorAndPushResult();

        verify(valStack).pull();
        verify(opStack).pull();
        verify(unaryOperator).execute(opContextCap.capture());

        UnaryOperatorContext opContext = opContextCap.getValue();
        assertEquals(2d,opContext.getValue(), 1);

        verify(valStack).push(3D);
    }

    @Test
    public void testCallBinaryOperator() {
        when(binaryOperator.execute(any(BinaryOperatorContext.class))).thenReturn(3D);
        when(valStack.pull()).thenReturn(2D,1D);
        doReturn(binaryOperator).when(opStack).pull();

        ArgumentCaptor<BinaryOperatorContext> opContextCap = ArgumentCaptor.forClass(BinaryOperatorContext.class);

        interpreter.callLastOperatorAndPushResult();

        verify(valStack,times(2)).pull();
        verify(opStack).pull();
        verify(binaryOperator).execute(opContextCap.capture());

        BinaryOperatorContext opContext = opContextCap.getValue();
        assertEquals(1d,opContext.getFirstValue(),1);
        assertEquals(2d,opContext.getSecondValue(),1);

        verify(valStack).push(3D);
    }

    @Test
    public void testCallFunction() {
        when(functionOperator.execute(any(FunctionOperatorContext.class))).thenReturn(5D);
        when(valStack.pull()).thenReturn(2D,1D, 4D);
        doReturn(functionOperator).when(opStack).pull();

        ArgumentCaptor<FunctionOperatorContext> opContextCap = ArgumentCaptor
                .forClass(FunctionOperatorContext.class);

        interpreter.callLastOperatorAndPushResult();

        verify(valStack,times(3)).pull();
        verify(opStack).pull();
        verify(functionOperator).execute(opContextCap.capture());

        FunctionOperatorContext opContext = opContextCap.getValue();
        assertEquals(4d,opContext.param(0),1);
        assertEquals(1d,opContext.param(1),1);
        assertEquals(2d,opContext.param(2),1);

        verify(valStack).push(5D);
    }
}