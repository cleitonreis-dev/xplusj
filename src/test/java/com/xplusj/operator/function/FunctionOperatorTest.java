package com.xplusj.operator.function;

import com.xplusj.GlobalContext;
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
public class FunctionOperatorTest {

    private static final double DELTA = 0.00000000000000000001D;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private GlobalContext context;

    @Mock
    private Function<FunctionOperatorContext,Double> function;

    @Test
    public void testOperatorCall(){
        FunctionOperatorDefinition definition = FunctionOperatorDefinition.create("sum(a,b,c)", function);
        double[] params = {1D,2D,3D};
        double expectedValue = 3;

        when(function.apply(any(FunctionOperatorContext.class))).thenReturn(expectedValue);
        ArgumentCaptor<FunctionOperatorContext> ctxCaptor = ArgumentCaptor.forClass(FunctionOperatorContext.class);

        FunctionOperator operator = FunctionOperator.create(context,definition);
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
        FunctionOperatorDefinition definition = FunctionOperatorDefinition.create("sum(a,b,c)", function);
        double[] params = {1D,2D,3D};
        double expectedValue = 3;

        when(function.apply(any(FunctionOperatorContext.class))).thenReturn(expectedValue);
        ArgumentCaptor<FunctionOperatorContext> ctxCaptor = ArgumentCaptor.forClass(FunctionOperatorContext.class);

        FunctionOperator operator = FunctionOperator.create(context,definition);
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
        FunctionOperatorDefinition definition = FunctionOperatorDefinition.create("sum(a,b,c)", function);
        double[] params = {1D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 1");

        FunctionOperator operator = FunctionOperator.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams1(){
        FunctionOperatorDefinition definition = FunctionOperatorDefinition.create("sum(a,b,c)", function);
        double[] params = {1D,2D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 2");

        FunctionOperator operator = FunctionOperator.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams2(){
        FunctionOperatorDefinition definition = FunctionOperatorDefinition.create("sum(a,b,c)", function);
        double[] params = {};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 0");

        FunctionOperator operator = FunctionOperator.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams3(){
        FunctionOperatorDefinition definition = FunctionOperatorDefinition.create("sum(a,b,c)", function);
        double[] params = {1D,2D,3D,4D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' expects 3 parameter(s), but received 4");

        FunctionOperator operator = FunctionOperator.create(context,definition);
        operator.execute(params);
    }
}