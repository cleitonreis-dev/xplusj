package com.xplusj.context;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultExpressionOperatorExecutorDefinitionsTest {

    @Test
    public void hasFunction() {
        FunctionOperator definition = FunctionOperator
                .func("sum(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

        ExpressionOperatorDefinitions repository = DefaultExpressionOperatorDefinitions.builder()
            .addFunction(definition)
            .build();

        assertTrue(repository.hasFunction("sum"));
        assertEquals(definition, repository.getFunction("sum"));
    }

    @Test
    public void hasBinaryOperator() {
        BinaryOperator definition = BinaryOperator
                .binary("+", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

        ExpressionOperatorDefinitions repository = DefaultExpressionOperatorDefinitions.builder()
                .addBinaryOperator(definition)
                .build();

        assertTrue(repository.hasBinaryOperator("+"));
        assertEquals(definition, repository.getBinaryOperator("+"));
    }

    @Test
    public void hasUnaryOperator() {
        UnaryOperator definition = UnaryOperator
                .unary("-", Precedence.low(), (ctx)->-ctx.param());

        ExpressionOperatorDefinitions repository = DefaultExpressionOperatorDefinitions.builder()
                .addUnaryOperator(definition)
                .build();

        assertTrue(repository.hasUnaryOperator("-"));
        assertEquals(definition, repository.getUnaryOperator("-"));
    }

    @Test
    public void hasConstant() {
        ExpressionOperatorDefinitions repository = DefaultExpressionOperatorDefinitions.builder()
                .addConstant(Constant.newConst("PI",Math.PI))
                .build();

        assertTrue(repository.hasConstant("PI"));
        assertEquals(Math.PI, repository.getConstant("PI").getValue(), 0.000000000000001);
    }

    @Test
    public void defaultBuilder() {
        ExpressionOperatorDefinitions repository = ExpressionOperatorDefinitions.builder().build();
        assertTrue(repository instanceof DefaultExpressionOperatorDefinitions);
    }
}