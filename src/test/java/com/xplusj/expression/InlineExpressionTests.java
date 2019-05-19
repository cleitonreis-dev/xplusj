package com.xplusj.expression;

import com.xplusj.GlobalContext;
import com.xplusj.context.DefaultGlobalContext;
import com.xplusj.context.DefaultVariableContext;
import com.xplusj.interpreter.ExpressionParser;
import com.xplusj.operator.BinaryOperator;
import com.xplusj.operator.FunctionOperator;
import com.xplusj.operator.UnaryOperator;
import org.junit.Test;

import static com.xplusj.operator.Precedence.*;
import static org.junit.Assert.assertEquals;

public class InlineExpressionTests {
    static final GlobalContext CONTEXT =
        DefaultGlobalContext.builder()
            .addBinaryOperator(BinaryOperator.create('+', low(), ctx->ctx.getFirstValue() + ctx.getSecondValue()))
            .addBinaryOperator(BinaryOperator.create('-', low(), ctx->ctx.getFirstValue() - ctx.getSecondValue()))
            .addBinaryOperator(BinaryOperator.create('*', high(), ctx->ctx.getFirstValue() * ctx.getSecondValue()))
            .addBinaryOperator(BinaryOperator.create('/', high(), ctx->ctx.getFirstValue() / ctx.getSecondValue()))
            .addUnaryOperator(UnaryOperator.create('+', higherThan(high()), ctx->+ctx.getValue()))
            .addUnaryOperator(UnaryOperator.create('-', higherThan(high()), ctx->-ctx.getValue()))
            .addFunction(FunctionOperator.create("max(a,b)", ctx->Math.max(ctx.param("a"), ctx.param("b"))))
        .build();

    static final ExpressionParser PARSER = new com.xplusj.interpreter.parser.ExpressionParser(CONTEXT);

    @Test
    public void eval() {
        String expression = "1+1";
        assertEquals(expression, 2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval1() {
        String expression = "2+3-2";
        assertEquals(expression, 3D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval2() {
        String expression = "2+4/2";
        assertEquals(expression, 4D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval3() {
        String expression = "2+3*2";
        assertEquals(expression, 8D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval4() {
        String expression = "2+(3-2)";
        assertEquals(expression, 3D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval5() {
        String expression = "(2+4)/2";
        assertEquals(expression, 3D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval6() {
        String expression = "(2+3)*2";
        assertEquals(expression, 10D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval7() {
        String expression = "-2";
        assertEquals(expression, -2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval8() {
        String expression = "+2";
        assertEquals(expression, 2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval9() {
        String expression = "(-2)";
        assertEquals(expression, -2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval10() {
        String expression = "(+2)";
        assertEquals(expression, 2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval11() {
        String expression = "4+-2";
        assertEquals(expression, 2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval12() {
        String expression = "4+(-2)";
        assertEquals(expression, 2D, eval(expression).eval(), 0D);
    }

    @Test
    public void eval13() {
        String expression = "4+a";
        assertEquals(expression, 6D, eval(expression).eval(DefaultVariableContext
                .builder().add("a", 2D).build()), 0D);
    }

    private static InlineExpression eval(String expression){
        return new InlineExpression(expression, CONTEXT, PARSER);
    }

    /*private Environment env = defaultEnv().build();
    private ExpressionFactory factory = defaultFactory(env);

    @Test
    public void testPlus(){
        double result = factory.expression("1+1").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusAndMinus(){
        double result = factory.expression("3+1-3").eval();
        assertEquals(1D, result, 0);
    }

    @Test
    public void testMultiplyPrecedence(){
        double result = factory.expression("1+1*2").eval();
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyAndDivision(){
        double result = factory.expression("2*2/4").eval();
        assertEquals(1D, result, 0);
    }

    @Test
    public void testDivisionAndPower(){
        double result = factory.expression("8/2^2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testMultiplyAndSqrt(){
        double result = factory.expression("2*#9").eval();
        assertEquals(6D, result, 0);
    }

    @Test
    public void testMinusUnaryOperator(){
        double result = factory.expression("-2").eval();
        assertEquals(-2D, result, 0);
    }

    @Test
    public void testMinusUnaryOperatorWithinParenthesis(){
        double result = factory.expression("(-2)").eval();
        assertEquals(-2D, result, 0);
    }

    @Test
    public void testPlusUnaryOperator(){
        double result = factory.expression("+2").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusUnaryOperatorWithinParenthesis(){
        double result = factory.expression("(+2)").eval();
        assertEquals(2D, result, 0);
    }

    @Test
    public void testCallingFunction(){
        double result = factory.expression("max(3,2)").eval();
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyWithFunctionCall(){
        double result = factory.expression("2*max(3,2)").eval();
        assertEquals(6D, result, 0);
    }

    @Test
    public void testDividingFunctionsResult(){
        double result = factory.expression("max(3,2.99)*max(4,-4)").eval();
        assertEquals(12D, result, 0);
    }

    @Test
    public void testParenthesisPrecedence(){
        double result = factory.expression("2*(1+1)").eval();
        assertEquals(4D, result, 0);
    }

    @Test
    public void testFuncParameterWithInnerExpressionParenthesisPrecedence(){
        double result = factory.expression("2+max(3,(2*(3+2)))").eval();
        assertEquals(12D, result, 0);
    }

    @Test
    public void testMakeSureTheFunctionParamsAreGivenInCorrectOrder(){
        ExpressionFactory factory = defaultFactory(defaultEnv().functions(asList(
            function("minus(x,y)", c->c.param("x")-c.param("y"))
        )).build());

        double result = factory.expression("minus(10,5)").eval();
        assertEquals(5D, result, 0);
    }

    @Test
    public void testFunctionCallingAnotherFunction(){
        ExpressionFactory factory = defaultFactory(defaultEnv().functions(asList(
            function("foo(x)", c -> c.param("x") * 2),
            function("bar(y)", c -> 3 * c.call("foo", c.param("y")))
        )).build());

        double result = factory.expression("bar(3)").eval();
        assertEquals(18D, result, 0);
    }

    @Test
    public void testFunctionWithFunctionAsParam(){
        ExpressionFactory factory = defaultFactory(defaultEnv().functions(asList(
            function("foo(x,y)", c -> c.param("x") * c.param("y")),
            function("bar(a,b)", c -> c.param("a") + c.param("b"))
        )).build());

        double result = factory.expression("bar(3,foo(2,3))").eval();
        assertEquals(9D, result, 0);
    }

    @Test
    public void testConstantInTheMiddleOfExpression(){
        double result = factory.expression("3*PI/2").eval();
        assertEquals(3 * Math.PI / 2, result, 0);
    }

    @Test
    public void testConstantAtTheEndOfExpression(){
        double result = factory.expression("3*PI").eval();
        assertEquals(3 * Math.PI, result, 0);
    }

    @Test
    public void testUnaryOperatorWithConstant(){
        double result = factory.expression("-PI").eval();
        assertEquals(-Math.PI, result, 0);
    }

    @Test
    public void testFunctionCallingConstant(){
        ExpressionFactory factory = defaultFactory(defaultEnv().functions(asList(
            function("foo(x)", c -> c.param("x") * c.getConstant("PI"))
        )).build());

        double result = factory.expression("foo(2)").eval();
        assertEquals(2 * Math.PI, result, 0);
    }

    @Test
    public void testBinaryOperatorCallingConstant(){
        ExpressionFactory factory = defaultFactory(defaultEnv().binaryOperators(asList(
            Operator.binary('+', low(), c->(c.getFirstValue() + c.getSecondValue()) * c.getConstant("PI"))
        )).build());

        double result = factory.expression("2+2").eval();
        assertEquals((2 + 2) * Math.PI, result, 0);
    }*/
}