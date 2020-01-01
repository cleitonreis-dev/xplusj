package com.xplusj.operator;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperators;
import com.xplusj.operator.function.FunctionOperatorExecutor;
import com.xplusj.Variable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OperatorExecutorContextTest {

    private static final double DELTA = 0.00000000000000000001D;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private ExpressionContext context;

    @Mock
    private ExpressionOperators definitions;

    @Before
    public void setUp(){
        when(context.getDefinitions()).thenReturn(definitions);
    }

    @Test
    public void testFunctionCall(){
        String funcName = "sum";
        FunctionOperatorExecutor function = Mockito.mock(FunctionOperatorExecutor.class);
        double[] params = {1D,2D};

        when(definitions.hasFunction(funcName)).thenReturn(true);
        when(context.getFunction(funcName)).thenReturn(function);

        new OperatorExecutorContextTestImpl(context).call(funcName, params);

        verify(definitions).hasFunction(funcName);
        verify(function).execute(params);
    }

    @Test
    public void testFunctionCallNotFound(){
        String funcName = "sum";
        double[] params = {1D,2D};

        when(definitions.hasFunction(funcName)).thenReturn(false);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' not found");

        new OperatorExecutorContextTestImpl(context).call(funcName, params);
    }

    @Test
    public void testGetConstant(){
        String constName = "CONST";
        double constValue = 10D;

        when(definitions.hasConstant(constName)).thenReturn(true);
        when(definitions.getConstant(constName)).thenReturn(Variable.var(constName,constValue));

        double value = new OperatorExecutorContextTestImpl(context).getConstant(constName);
        assertEquals(constValue,value,DELTA);
    }

    @Test
    public void testGetConstantNotFound(){
        String constName = "CONST";

        when(definitions.hasConstant(constName)).thenReturn(false);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Constant 'CONST' not found");

        new OperatorExecutorContextTestImpl(context).getConstant(constName);
    }

    @Test
    public void testGetParamIndex(){
        double[] params = {1D,2D};
        OperatorExecutorContextTestImpl oContext = new OperatorExecutorContextTestImpl(context,params);
        assertEquals(params[0], oContext.param(0), DELTA);
        assertEquals(params[1], oContext.param(1), DELTA);
    }

    @Test
    public void testGetParamInvalidIndex(){
        double[] params = {1D,2D};
        OperatorExecutorContextTestImpl oContext = new OperatorExecutorContextTestImpl(context,params);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid param index '-1'. Valid indexes are from 0 to 1");

        oContext.param(-1);
    }

    @Test
    public void testGetParamInvalidIndex2(){
        double[] params = {1D,2D};
        OperatorExecutorContextTestImpl oContext = new OperatorExecutorContextTestImpl(context,params);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid param index '2'. Valid indexes are from 0 to 1");

        oContext.param(2);
    }

    static class OperatorExecutorContextTestImpl extends OperatorContext{

        OperatorExecutorContextTestImpl(ExpressionContext context, double...params) {
            super(context,params);
        }
    }
}