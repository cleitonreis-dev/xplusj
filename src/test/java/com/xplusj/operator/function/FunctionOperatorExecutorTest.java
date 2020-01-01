package com.xplusj.operator.function;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperators;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FunctionOperatorExecutorTest {

    private static final double DELTA = 0.00000000000000000001D;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private ExpressionContext context = ExpressionContext.builder().build();

    @Mock
    private Function<FunctionOperatorContext,Double> function;

    @Test
    public void testOperatorCall(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,c)", function);
        double[] params = {1D,2D,3D};
        double expectedValue = 3;

        when(function.apply(any(FunctionOperatorContext.class))).thenReturn(expectedValue);
        ArgumentCaptor<FunctionOperatorContext> ctxCaptor = ArgumentCaptor.forClass(FunctionOperatorContext.class);

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        double result = operator.execute(params);

        assertEquals(expectedValue, result, DELTA);
        assertEquals(definition, operator.getDefinition());

        verify(function).apply(ctxCaptor.capture());

        FunctionOperatorContext operatorContext = ctxCaptor.getValue();
        assertEquals(params[0], operatorContext.param("a"), DELTA);
        assertEquals(params[1], operatorContext.param("b"), DELTA);
        assertEquals(params[2], operatorContext.param("c"), DELTA);
    }

    @Test
    public void testOperatorCall2(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,c)", function);
        double[] params = {1D,2D,3D};
        double expectedValue = 3;

        when(function.apply(any(FunctionOperatorContext.class))).thenReturn(expectedValue);
        ArgumentCaptor<FunctionOperatorContext> ctxCaptor = ArgumentCaptor.forClass(FunctionOperatorContext.class);

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        double result = operator.execute(params);

        assertEquals(expectedValue, result, DELTA);
        assertEquals(definition, operator.getDefinition());

        verify(function).apply(ctxCaptor.capture());

        FunctionOperatorContext operatorContext = ctxCaptor.getValue();
        assertEquals(params[0], operatorContext.param(0), DELTA);
        assertEquals(params[1], operatorContext.param(1), DELTA);
        assertEquals(params[2], operatorContext.param(2), DELTA);
    }

    @Test
    public void testOperatorParams(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,c)", function);
        double[] params = {1D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 1");

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams1(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,c)", function);
        double[] params = {1D,2D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 2");

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams2(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,c)", function);
        double[] params = {};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 0");

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams3(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,c)", function);
        double[] params = {1D,2D,3D,4D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 4");

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams4(){
        FunctionOperator definition = FunctionOperator.func("sum(a,b,...)", function);
        double[] params = {1D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects at least 2 parameter(s), but received 1");

        FunctionOperatorExecutor operator = FunctionOperatorExecutor.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testCompiledFunction(){
        double a = 2;
        double b = 3;

        ExpressionContext ctx = context.append(ExpressionOperators.builder()
            .addFunction(FunctionOperator.func("test(a,b)", "pow(max(a,b),2)"))
            .build()
        );

        double expected = ctx.getFunction("pow").execute(ctx.getFunction("max").execute(a,b), 2);
        assertEquals(expected, ctx.getFunction("test").execute(a,b), DELTA);
    }
}