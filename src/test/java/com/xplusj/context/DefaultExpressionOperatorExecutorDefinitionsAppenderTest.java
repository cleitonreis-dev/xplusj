package com.xplusj.context;

import com.xplusj.ExpressionOperators;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.Variable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultExpressionOperatorExecutorDefinitionsAppenderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static FunctionOperator sum = FunctionOperator
            .func("sum(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

    private static BinaryOperator plus = BinaryOperator
            .binary("+", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

    private static UnaryOperator minus = UnaryOperator
            .unary("-", Precedence.low(), (ctx)->-ctx.param());
    
    private static ExpressionOperators parentDefinitions = DefaultExpressionOperators.builder()
            .addFunction(sum)
            .addBinaryOperator(plus)
            .addUnaryOperator(minus)
            .addConstant(Variable.var("PI",Math.PI))
            .build();

    @Test
    public void hasFunction() {
        FunctionOperator definition = FunctionOperator
                .func("sum(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
            .append(parentDefinitions,
                DefaultExpressionOperators.builder()
                    .addFunction(definition)
                    .build()
            );

        assertTrue(definitions.hasFunction("sum"));
        assertEquals(definition, definitions.getFunction("sum"));
    }

    @Test
    public void hasBinaryOperator() {
        BinaryOperator definition = BinaryOperator
                .binary("+", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
            .append(parentDefinitions, DefaultExpressionOperators.builder()
                .addBinaryOperator(definition)
                .build()
            );

        assertTrue(definitions.hasBinaryOperator("+"));
        assertEquals(definition, definitions.getBinaryOperator("+"));
    }

    @Test
    public void hasUnaryOperator() {
        UnaryOperator definition = UnaryOperator
                .unary("-", Precedence.low(), (ctx)->-ctx.param());

        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
            .append(parentDefinitions, DefaultExpressionOperators.builder()
                .addUnaryOperator(definition)
                .build()
            );

        assertTrue(definitions.hasUnaryOperator("-"));
        assertEquals(definition, definitions.getUnaryOperator("-"));
    }

    @Test
    public void hasConstant() {
        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
            .append(parentDefinitions, DefaultExpressionOperators.builder()
                .addConstant(Variable.var("PI",Math.PI))
                .build()
            );

        assertTrue(definitions.hasConstant("PI"));
        assertEquals(Math.PI, definitions.getConstant("PI").getValue(), 0.000000000000001);
    }

    @Test
    public void hasFunctionFromParent() {
        FunctionOperator definition = FunctionOperator
                .func("sum2(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
                .append(parentDefinitions,
                        DefaultExpressionOperators.builder()
                                .addFunction(definition)
                                .build()
                );

        assertTrue(definitions.hasFunction("sum"));
        assertEquals(sum, definitions.getFunction("sum"));
    }

    @Test
    public void hasBinaryOperatorFromParent() {
        BinaryOperator definition = BinaryOperator
                .binary("%", Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
                .append(parentDefinitions, DefaultExpressionOperators.builder()
                        .addBinaryOperator(definition)
                        .build()
                );

        assertTrue(definitions.hasBinaryOperator("+"));
        assertEquals(plus, definitions.getBinaryOperator("+"));
    }

    @Test
    public void hasUnaryOperatorFromParent() {
        UnaryOperator definition = UnaryOperator
                .unary("%", Precedence.low(), (ctx)->-ctx.param());

        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
                .append(parentDefinitions, DefaultExpressionOperators.builder()
                        .addUnaryOperator(definition)
                        .build()
                );

        assertTrue(definitions.hasUnaryOperator("-"));
        assertEquals(minus, definitions.getUnaryOperator("-"));
    }

    @Test
    public void hasConstantFromParent() {
        ExpressionOperators definitions = DefaultExpressionOperatorsAppender
                .append(parentDefinitions, DefaultExpressionOperators.builder()
                        .addConstant(Variable.var("AA",3D))
                        .build()
                );

        assertTrue(definitions.hasConstant("PI"));
        assertEquals(Math.PI, definitions.getConstant("PI").getValue(), 0.000000000000001);
    }

    @Test
    public void requiresParentDefinitions(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parent instance definition is required");

        DefaultExpressionOperatorsAppender.append(null, ExpressionOperators.builder().build());
    }

    @Test
    public void requiresCurrentDefinitions(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Current instance definition is required");

        DefaultExpressionOperatorsAppender.append(parentDefinitions, null);
    }
}