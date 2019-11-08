package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorContext;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.function.FunctionOperatorContext;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.operator.unary.UnaryOperatorContext;
import com.xplusj.operator.unary.UnaryOperatorDefinition;
import com.xplusj.parser.DefaultExpressionParser;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.DefaultExpressionTokenizer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwoStackBasedProcessorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private Environment environment;

    @Mock
    private GlobalContext globalContext;

    private FunctionOperator functionOperator;

    @Mock
    private FunctionOperatorDefinition functionOperatorDefinition;

    private UnaryOperator unaryOperator;

    @Mock
    private UnaryOperatorDefinition unaryOperatorDefinition;

    private BinaryOperator binaryOperator;

    @Mock
    private BinaryOperatorDefinition binaryOperatorDefinition;

    @Mock
    private Stack<Double> valStack;

    @Mock
    private Stack<Operator<?>> opStack;

    @Mock
    private VariableContext variableContext;

    private TwoStackBasedProcessor interpreter;

    @Mock
    private Function<UnaryOperatorContext,Double> unaryFunction;

    @Mock
    private Function<BinaryOperatorContext,Double> binaryFunction;

    @Mock
    private Function<FunctionOperatorContext,Double> functionFunction;

    @Before
    public void setUp(){
        when(environment.getContext()).thenReturn(globalContext);

        ExpressionParser parser = DefaultExpressionParser.create(globalContext, DefaultExpressionTokenizer.create(globalContext));
        when(environment.getParser()).thenReturn(parser);

        when(unaryOperatorDefinition.getType()).thenReturn(OperatorType.UNARY);
        when(unaryOperatorDefinition.getParamsLength()).thenReturn(1);
        when(unaryOperatorDefinition.getFunction()).thenReturn(unaryFunction);
        when(binaryOperatorDefinition.getType()).thenReturn(OperatorType.BINARY);
        when(binaryOperatorDefinition.getParamsLength()).thenReturn(2);
        when(binaryOperatorDefinition.getFunction()).thenReturn(binaryFunction);
        when(functionOperatorDefinition.getType()).thenReturn(OperatorType.FUNCTION);
        when(functionOperatorDefinition.getParamsLength()).thenReturn(3);
        when(functionOperatorDefinition.getFunction()).thenReturn(functionFunction);

        interpreter = new TwoStackBasedProcessor(environment, variableContext, valStack, opStack);

        unaryOperator = UnaryOperator.create(globalContext,unaryOperatorDefinition);
        binaryOperator = BinaryOperator.create(globalContext,binaryOperatorDefinition);
        functionOperator = FunctionOperator.create(globalContext,functionOperatorDefinition);
    }

    @Test
    public void pushValue() {
        interpreter.addValue(1D);
        verify(valStack).push(1D);
    }

    @Test
    public void pushVar() {
        when(variableContext.contains("a")).thenReturn(true);
        when(variableContext.value("a")).thenReturn(2D);

        interpreter.addVar("a");

        verify(valStack).push(2D);
    }

    @Test
    public void pushVar2() {
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Variable 'a' not found");

        when(variableContext.contains("a")).thenReturn(false);

        interpreter.addVar("a");
    }

    @Test
    public void pushConstant() {
        when(globalContext.hasConstant("AB")).thenReturn(true);
        when(globalContext.getConstant("AB")).thenReturn(3D);

        interpreter.addConstant("AB");

        verify(valStack).push(3D);
    }

    @Test
    public void pushConstant2() {
        thrown.expect(ExpressionException.class);
        thrown.expectMessage("Constant 'AB' not found");

        when(globalContext.hasConstant("AB")).thenReturn(false);

        interpreter.addConstant("AB");
    }

    @Test
    public void pushOperator() {
        interpreter.addOperator(unaryOperator);
        verify(opStack).push(unaryOperator);
    }

    @Test
    public void getLastOperator() {
        interpreter.getLastOperator();
        verify(opStack).peek();
    }

    @Test
    public void testCallUnaryOperator() {
        when(unaryFunction.apply(any(UnaryOperatorContext.class))).thenReturn(3D);
        when(valStack.pull()).thenReturn(2D);
        doReturn(unaryOperator).when(opStack).pull();

        ArgumentCaptor<UnaryOperatorContext> opContextCap = ArgumentCaptor.forClass(UnaryOperatorContext.class);

        interpreter.callLastOperatorAndAddResult();

        verify(valStack).pull();
        verify(opStack).pull();
        verify(unaryFunction).apply(opContextCap.capture());

        UnaryOperatorContext opContext = opContextCap.getValue();
        assertEquals(2d,opContext.getValue(), 1);

        verify(valStack).push(3D);
    }

    @Test
    public void testCallBinaryOperator() {
        when(binaryFunction.apply(any(BinaryOperatorContext.class))).thenReturn(3D);
        when(valStack.pull()).thenReturn(2D,1D);
        doReturn(binaryOperator).when(opStack).pull();

        ArgumentCaptor<BinaryOperatorContext> opContextCap = ArgumentCaptor.forClass(BinaryOperatorContext.class);

        interpreter.callLastOperatorAndAddResult();

        verify(valStack,times(2)).pull();
        verify(opStack).pull();
        verify(binaryFunction).apply(opContextCap.capture());

        BinaryOperatorContext opContext = opContextCap.getValue();
        assertEquals(1d,opContext.getFirstValue(),1);
        assertEquals(2d,opContext.getSecondValue(),1);

        verify(valStack).push(3D);
    }

    @Test
    public void testCallFunction() {
        when(functionFunction.apply(any(FunctionOperatorContext.class))).thenReturn(5D);
        when(valStack.pull()).thenReturn(2D,1D, 4D);
        doReturn(functionOperator).when(opStack).pull();

        ArgumentCaptor<FunctionOperatorContext> opContextCap = ArgumentCaptor
                .forClass(FunctionOperatorContext.class);

        interpreter.callLastOperatorAndAddResult();

        verify(valStack,times(3)).pull();
        verify(opStack).pull();
        verify(functionFunction).apply(opContextCap.capture());

        FunctionOperatorContext opContext = opContextCap.getValue();
        assertEquals(4d,opContext.param(0),1);
        assertEquals(1d,opContext.param(1),1);
        assertEquals(2d,opContext.param(2),1);

        verify(valStack).push(5D);
    }
}