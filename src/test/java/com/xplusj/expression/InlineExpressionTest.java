package com.xplusj.expression;

import com.xplusj.ExpressionGlobalContext;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.VariableContext;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.xplusj.operator.Precedence.*;
import static com.xplusj.variable.Variable.var;
import static org.junit.Assert.assertEquals;

@Deprecated //TODO adapt this to integration test
@RunWith(MockitoJUnitRunner.class)
public class InlineExpressionTest {
    private static final ExpressionOperatorDefinitions CONTEXT =
            ExpressionOperatorDefinitions.builder()
            .addBinaryOperator(BinaryOperator.binary("+", low(), ctx->ctx.param0() + ctx.param1()))
            .addBinaryOperator(BinaryOperator.binary("-", low(), ctx->ctx.param0() - ctx.param1()))
            .addBinaryOperator(BinaryOperator.binary("*", high(), ctx->ctx.param0() * ctx.param1()))
            .addBinaryOperator(BinaryOperator.binary("/", high(), ctx->ctx.param0() / ctx.param1()))
            .addUnaryOperator(UnaryOperator.unary("+", higherThan(high()), ctx->+ctx.param()))
            .addUnaryOperator(UnaryOperator.unary("-", higherThan(high()), ctx->-ctx.param()))
            .addFunction(FunctionOperator.func("max(a,b)", ctx->Math.max(ctx.param("a"), ctx.param("b"))))
        .build();


    private static final ExpressionGlobalContext ENV = ExpressionGlobalContext.builder()
            .setOperatorDefinitions(CONTEXT)
            .build();

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
        assertEquals(expression, 6D, eval(expression).eval(VariableContext
                .vars(var("a", 2D))), 0D);
    }

    private static DefaultExpression eval(String expression){
        return DefaultExpression.create(expression, ENV.getParser(),
                (ctx)->TwoStackBasedProcessor.create(ENV,ctx));
    }
}