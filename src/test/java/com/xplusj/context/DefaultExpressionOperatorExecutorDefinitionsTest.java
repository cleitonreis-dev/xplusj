package com.xplusj.context;

import com.xplusj.ExpressionOperators;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.Variable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultExpressionOperatorExecutorDefinitionsTest {

    @Test
    public void hasFunction() {
        FunctionOperator definition = FunctionOperator
                .func("sum(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

        ExpressionOperators repository = DefaultExpressionOperators.builder()
            .addFunction(definition)
            .build();

        assertTrue(repository.hasFunction("sum"));
        assertEquals(definition, repository.getFunction("sum"));
    }

    @Test
    public void hasBinaryOperator() {
        BinaryOperator definition = BinaryOperator
                .binary("+", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

        ExpressionOperators repository = DefaultExpressionOperators.builder()
                .addBinaryOperator(definition)
                .build();

        assertTrue(repository.hasBinaryOperator("+"));
        assertEquals(definition, repository.getBinaryOperator("+"));
    }

    @Test
    public void hasUnaryOperator() {
        UnaryOperator definition = UnaryOperator
                .unary("-", Precedence.low(), (ctx)->-ctx.param());

        ExpressionOperators repository = DefaultExpressionOperators.builder()
                .addUnaryOperator(definition)
                .build();

        assertTrue(repository.hasUnaryOperator("-"));
        assertEquals(definition, repository.getUnaryOperator("-"));
    }

    @Test
    public void hasConstant() {
        ExpressionOperators repository = DefaultExpressionOperators.builder()
                .addConstant(Variable.var("PI",Math.PI))
                .build();

        assertTrue(repository.hasConstant("PI"));
        assertEquals(Math.PI, repository.getConstant("PI").getValue(), 0.000000000000001);
    }

    @Test
    public void defaultBuilder() {
        ExpressionOperators repository = ExpressionOperators.builder().build();
        assertTrue(repository instanceof DefaultExpressionOperators);
    }
}