package com.xplusj.operator.binary;

import com.xplusj.ExpressionContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Function;

import static com.xplusj.operator.Precedence.low;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BinaryOperatorTest {

    private static final double DELTA = 0.00000000000000000001D;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ExpressionContext context;

    @Mock
    private Function<BinaryOperatorContext,Double> function;

    @Test
    public void testOperatorCall(){
        BinaryOperatorDefinition definition = BinaryOperatorDefinition.create('+', low(), function);
        double[] params = {1D,2D};
        double expectedValue = 3;

        when(function.apply(any(BinaryOperatorContext.class))).thenReturn(expectedValue);
        ArgumentCaptor<BinaryOperatorContext> ctxCaptor = ArgumentCaptor.forClass(BinaryOperatorContext.class);

        BinaryOperator operator = BinaryOperator.create(context,definition);
        double result = operator.execute(params);

        assertEquals(expectedValue, result, DELTA);
        assertEquals(definition, operator.getDefinition());

        verify(function).apply(ctxCaptor.capture());

        BinaryOperatorContext operatorContext = ctxCaptor.getValue();
        assertEquals(params[0], operatorContext.param0(), DELTA);
        assertEquals(params[1], operatorContext.param1(), DELTA);
    }

    @Test
    public void testOperatorParams(){
        BinaryOperatorDefinition definition = BinaryOperatorDefinition.create('+', low(), function);
        double[] params = {1D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Binary operator + expects two parameters, but received 1");

        BinaryOperator operator = BinaryOperator.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams1(){
        BinaryOperatorDefinition definition = BinaryOperatorDefinition.create('+', low(), function);
        double[] params = {1D,2D,3D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Binary operator + expects two parameters, but received 3");

        BinaryOperator operator = BinaryOperator.create(context,definition);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams2(){
        BinaryOperatorDefinition definition = BinaryOperatorDefinition.create('+', low(), function);
        double[] params = {};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Binary operator + expects two parameters, but received 0");

        BinaryOperator operator = BinaryOperator.create(context,definition);
        operator.execute(params);
    }
}