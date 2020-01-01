package com.xplusj.expression;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import org.junit.Before;
import org.junit.Test;

import static com.xplusj.VariableContext.vars;
import static com.xplusj.variable.Variable.var;
import static org.junit.Assert.assertEquals;

public class DefaultExpressionIntegrationTest {

    private static final double DELTA = 0D;
    private static ExpressionContext context = ExpressionContext.builder().build();

    @Before
    public void setUp(){

    }

    @Test
    public void testUnarySquare(){
        assertEquals(2*2D, context.expression("+**2").eval(), DELTA);
    }

    @Test
    public void testUnarySquare2(){
        assertEquals(-(2*2D), context.expression("-**2").eval(), DELTA);
    }

    @Test
    public void testUnaryCube(){
        assertEquals(Math.pow(2,3), context.expression("+***2").eval(), DELTA);
    }

    @Test
    public void testUnaryCube2(){
        assertEquals(-Math.pow(2,3), context.expression("-***2").eval(), DELTA);
    }

    @Test
    public void testConditionalIf(){
        assertEquals(34, context.expression("if(2==2, a, PI)").eval(vars(var("a", 34D))), DELTA);
    }

    @Test
    public void testConditionalIf2(){
        assertEquals(Math.PI, context.expression("if(2==a, a, PI)").eval(vars(var("a", 3D))), DELTA);
    }

    @Test
    public void testConditionalIf3(){
        assertEquals(2D, context.expression("if(2>=a, a, PI)").eval(vars(var("a", 2D))), DELTA);
    }

    @Test
    public void testSamePrecedence(){
        double result = context.expression("3+1-3").eval();
        assertEquals(1D, result, 0);
    }

    @Test
    public void testSamePrecedence2(){
        double result = context.expression("3*2/3").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testDifferentPrecedence(){
        double result = context.expression("1+1*2").eval();
        assertEquals(3D, result, 0);
    }

    @Test
    public void testDifferentPrecedence2(){
        double result = context.expression("2*3-2").eval();
        assertEquals(4D, result, 0);
    }

    @Test
    public void testDifferentPrecedence3(){
        double result = context.expression("8/**2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testDifferentPrecedence4(){
        double result = context.expression("8/2^2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testMultiplyAndSqrt(){
        double result = context.expression("2*#9").eval();
        assertEquals(6D, result, 0);
    }

    @Test
    public void testMinusUnaryOperator(){
        double result = context.expression("-2").eval();
        assertEquals(-2D, result, 0);
    }

    @Test
    public void testMinusUnaryOperatorWithinParenthesis(){
        double result = context.expression("(-2)").eval();
        assertEquals(-2D, result, 0);
    }

    @Test
    public void testPlusUnaryOperator(){
        double result = context.expression("+2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusUnaryOperatorWithinParenthesis(){
        double result = context.expression("(+2)").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testCallingFunction(){
        double result = context.expression("max(3,2,1.2,2.9999)").eval();
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyWithFunctionCall(){
        double result = context.expression("2*max(3,2)").eval();
        assertEquals(6D, result, 0);
    }

    @Test
    public void testDividingFunctionsResult(){
        double result = context.expression("max(3,2.99)*max(4,-4)").eval();
        assertEquals(12D, result, 0);
    }

    @Test
    public void testParenthesisPrecedence(){
        double result = context.expression("2*(1+1)").eval();
        assertEquals(4D, result, 0);
    }

    @Test
    public void testFuncParameterWithInnerExpressionParenthesisPrecedence(){
        double result = context.expression("2+max(3,(2*(3+2)))").eval();
        assertEquals(12D, result, 0);
    }

    @Test
    public void testMakeSureTheFunctionParamsAreGivenInCorrectOrder(){
        ExpressionContext globalContext = context.append(ExpressionOperatorDefinitions.builder()
                .addFunction(FunctionOperator.func("minus(x,y)", c->c.param("x")-c.param("y")))
                .build());

        double result = globalContext.expression("minus(10,5)").eval();
        assertEquals(5D, result, 0);
    }

    @Test
    public void testFunctionCallingAnotherFunction(){
        ExpressionContext globalContext = context.append(ExpressionOperatorDefinitions.builder()
                .addFunction(FunctionOperator.func("foo(x)", c -> c.param("x") * 2))
                .addFunction(FunctionOperator.func("bar(y)", c -> 3 * c.call("foo", c.param("y"))))
                .build());

        double result = globalContext.expression("bar(3)").eval();
        assertEquals(18D, result, 0);
    }

    @Test
    public void testFunctionWithFunctionAsParam(){
        ExpressionContext globalContext = context.append(ExpressionOperatorDefinitions.builder()
                .addFunction(FunctionOperator.func("foo(x,y)", c -> c.param("x") * c.param("y")))
                .addFunction(FunctionOperator.func("bar(a,b)", c -> c.param("a") + c.param("b")))
                .build());

        double result = globalContext.expression("bar(3,foo(2,3))").eval();
        assertEquals(9D, result, 0);
    }

    @Test
    public void testConstantInTheMiddleOfExpression(){
        double result = context.expression("3*PI/2").eval();
        assertEquals(3 * Math.PI / 2, result, 0);
    }

    @Test
    public void testConstantAtTheEndOfExpression(){
        double result = context.expression("3*PI").eval();
        assertEquals(3 * Math.PI, result, 0);
    }

    @Test
    public void testUnaryOperatorWithConstant(){
        double result = context.expression("-PI").eval();
        assertEquals(-Math.PI, result, 0);
    }

    @Test
    public void testFunctionCallingConstant(){
        ExpressionContext globalContext = context.append(ExpressionOperatorDefinitions.builder()
                .addFunction(FunctionOperator.func("foo(x)", c -> c.param("x") * c.getConstant("PI")))
                .build());

        double result = globalContext.expression("foo(2)").eval();
        assertEquals(2 * Math.PI, result, 0);
    }

    @Test
    public void testBinaryOperatorCallingConstant(){
        ExpressionContext globalContext = context.append(ExpressionOperatorDefinitions.builder()
                .addBinaryOperator(BinaryOperator.binary("+", Precedence.low(), c->(c.param0() + c.param1()) * c.getConstant("PI")))
                .build());

        double result = globalContext.expression("2+2").eval();
        assertEquals((2 + 2) * Math.PI, result, 0);
    }


}