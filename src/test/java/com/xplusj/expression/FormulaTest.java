package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.ExpressionContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.xplusj.VariableContext.vars;
import static com.xplusj.Variable.var;
import static org.junit.Assert.assertEquals;

@Deprecated //TODO adapt this to integration test
@RunWith(MockitoJUnitRunner.class)
public class FormulaTest {

    private static ExpressionContext context = ExpressionContext.builder().build();

    @Test
    public void testPlus(){
        double result = eval("a+a").eval(vars(var("a",1)));
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusAndMinus(){
        double result = eval("a+b-a").eval(vars(
                var("a", 3),
                var("b", 1D)
            ));

        assertEquals(1D, result, 0);
    }

    @Test
    public void testCallingFunction(){
        double result = eval("max(3,x)").eval(vars(var("x",3)));
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyWithFunctionCall(){
        double result = eval("ab*max(3,ab)").eval(vars(var("ab",2)));
        assertEquals(6D, result, 0);
    }

    private static Expression eval(String expression){
        return context.formula(expression);
    }

}