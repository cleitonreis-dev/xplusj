package com.xplusj.operator;

import com.xplusj.GlobalContext;
import com.xplusj.operator.function.FunctionOperator;
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
public class OperatorContextTest {

    private static final double DELTA = 0.00000000000000000001D;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private GlobalContext context;

    @Test
    public void testFunctionCall(){
        String funcName = "sum";
        FunctionOperator function = Mockito.mock(FunctionOperator.class);
        double[] params = {1D,2D};

        when(context.hasFunction(funcName)).thenReturn(true);
        when(context.getFunction(funcName)).thenReturn(function);

        new OperatorContextTestImpl(context).call(funcName, params);

        verify(context).hasFunction(funcName);
        verify(function).execute(params);
    }

    @Test
    public void testFunctionCallNotFound(){
        String funcName = "sum";
        double[] params = {1D,2D};

        when(context.hasFunction(funcName)).thenReturn(false);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function 'sum' not found");

        new OperatorContextTestImpl(context).call(funcName, params);
    }

    @Test
    public void testGetConstant(){
        String constName = "CONST";
        double constValue = 10D;

        when(context.hasConstant(constName)).thenReturn(true);
        when(context.getConstant(constName)).thenReturn(constValue);

        double value = new OperatorContextTestImpl(context).getConstant(constName);
        assertEquals(constValue,value,DELTA);
    }

    @Test
    public void testGetConstantNotFound(){
        String constName = "CONST";

        when(context.hasConstant(constName)).thenReturn(false);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Constant 'CONST' not found");

        new OperatorContextTestImpl(context).getConstant(constName);
    }

    @Test
    public void testGetParamIndex(){
        double[] params = {1D,2D};
        OperatorContextTestImpl oContext = new OperatorContextTestImpl(context,params);
        assertEquals(params[0], oContext.param(0), DELTA);
        assertEquals(params[1], oContext.param(1), DELTA);
    }

    @Test
    public void testGetParamInvalidIndex(){
        double[] params = {1D,2D};
        OperatorContextTestImpl oContext = new OperatorContextTestImpl(context,params);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid param index '-1'. Valid indexes are from 0 to 1");

        oContext.param(-1);
    }

    @Test
    public void testGetParamInvalidIndex2(){
        double[] params = {1D,2D};
        OperatorContextTestImpl oContext = new OperatorContextTestImpl(context,params);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid param index '2'. Valid indexes are from 0 to 1");

        oContext.param(2);
    }

    static class OperatorContextTestImpl extends OperatorContext{

        OperatorContextTestImpl(GlobalContext context, double...params) {
            super(context,params);
        }
    }
}