package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.function.ExpressionFunction;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InlineExpressionTests {

    private Environment env = Environment.defaultEnv().build();

    @Test
    public void testPlus(){
        double result = env.expression("1+1").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusAndMinus(){
        double result = env.expression("3+1-3").eval();
        assertEquals(1D, result, 0);
    }

    @Test
    public void testMultiplyPrecedence(){
        double result = env.expression("1+1*2").eval();
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyAndDivision(){
        double result = env.expression("2*2/4").eval();
        assertEquals(1D, result, 0);
    }

    @Test
    public void testDivisionAndPower(){
        double result = env.expression("8/2^2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testMultiplyAndSqrt(){
        double result = env.expression("2*#9").eval();
        assertEquals(6D, result, 0);
    }

    @Test
    public void testMinusUnaryOperator(){
        double result = env.expression("-2").eval();
        assertEquals(-2D, result, 0);
    }

    @Test
    public void testMinusUnaryOperatorWithinParenthesis(){
        double result = env.expression("(-2)").eval();
        assertEquals(-2D, result, 0);
    }

    @Test
    public void testPlusUnaryOperator(){
        double result = env.expression("+2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusUnaryOperatorWithinParenthesis(){
        double result = env.expression("(+2)").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testCallingFunction(){
        double result = env.expression("max(3,2)").eval();
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyWithFunctionCall(){
        double result = env.expression("2*max(3,2)").eval();
        assertEquals(6D, result, 0);
    }

    @Test
    public void testDividingFunctionsResult(){
        double result = env.expression("max(3,2.99)*max(4,-4)").eval();
        assertEquals(12D, result, 0);
    }

    @Test
    public void testParenthesisPrecedence(){
        double result = env.expression("2*(1+1)").eval();
        assertEquals(4D, result, 0);
    }

    @Test
    public void testFuncParameterWithInnerExpressionParenthesisPrecedence(){
        double result = env.expression("2+max(3,(2*(3+2)))").eval();
        assertEquals(12D, result, 0);
    }

    @Test
    public void testMakeSureTheFunctionParamsAreGivenInCorrectOrder(){
        Environment environment = Environment.defaultEnv().functions(Arrays.asList(
                ExpressionFunction.function("minus(x,y)", c->c.getParam("x")-c.getParam("y"))
        )).build();

        double result = environment.expression("minus(10,5)").eval();
        assertEquals(5D, result, 0);
    }
}