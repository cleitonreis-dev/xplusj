package com.xplusj.operator.unary;

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
public class UnaryOperatorTest {

    private static final double DELTA = 0.00000000000000000001D;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ExpressionContext context;

    @Mock
    private Function<UnaryOperatorContext,Double> function;

    @Test
    public void testOperatorCall(){
        UnaryOperatorDefinition definition = UnaryOperatorDefinition.create("+", low(), function);
        double[] params = {1D};
        double expectedValue = 3;

        when(function.apply(any(UnaryOperatorContext.class))).thenReturn(expectedValue);
        ArgumentCaptor<UnaryOperatorContext> ctxCaptor = ArgumentCaptor.forClass(UnaryOperatorContext.class);

        UnaryOperator operator = UnaryOperator.create(definition, context);
        double result = operator.execute(params);

        assertEquals(expectedValue, result, DELTA);
        assertEquals(definition, operator.getDefinition());

        verify(function).apply(ctxCaptor.capture());

        UnaryOperatorContext operatorContext = ctxCaptor.getValue();
        assertEquals(params[0], operatorContext.param(), DELTA);
    }

    @Test
    public void testOperatorParams(){
        UnaryOperatorDefinition definition = UnaryOperatorDefinition.create("+", low(), function);
        double[] params = {1D,2D};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unary operator + expects one parameter, but received 2");

        UnaryOperator operator = UnaryOperator.create(definition,context);
        operator.execute(params);
    }

    @Test
    public void testOperatorParams1(){
        UnaryOperatorDefinition definition = UnaryOperatorDefinition.create("+", low(), function);
        double[] params = {};

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unary operator + expects one parameter, but received 0");

        UnaryOperator operator = UnaryOperator.create(definition, context);
        operator.execute(params);
    }
}