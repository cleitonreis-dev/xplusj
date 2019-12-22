package com.xplusj.context;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultExpressionOperatorDefinitionsAppenderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static FunctionOperatorDefinition sum = FunctionOperatorDefinition
            .create("sum(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

    private static BinaryOperatorDefinition plus = BinaryOperatorDefinition
            .create('+', Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

    private static UnaryOperatorDefinition minus = UnaryOperatorDefinition
            .create('-', Precedence.low(), (ctx)->-ctx.param());
    
    private static ExpressionOperatorDefinitions parentDefinitions = DefaultExpressionOperatorDefinitions.builder()
            .addFunction(sum)
            .addBinaryOperator(plus)
            .addUnaryOperator(minus)
            .addConstant(Constant.newConst("PI",Math.PI))
            .build();

    @Test
    public void hasFunction() {
        FunctionOperatorDefinition definition = FunctionOperatorDefinition
                .create("sum(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
            .append(parentDefinitions,
                DefaultExpressionOperatorDefinitions.builder()
                    .addFunction(definition)
                    .build()
            );

        assertTrue(definitions.hasFunction("sum"));
        assertEquals(definition, definitions.getFunction("sum"));
    }

    @Test
    public void hasBinaryOperator() {
        BinaryOperatorDefinition definition = BinaryOperatorDefinition
                .create('+', Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
            .append(parentDefinitions,DefaultExpressionOperatorDefinitions.builder()
                .addBinaryOperator(definition)
                .build()
            );

        assertTrue(definitions.hasBinaryOperator('+'));
        assertEquals(definition, definitions.getBinaryOperator('+'));
    }

    @Test
    public void hasUnaryOperator() {
        UnaryOperatorDefinition definition = UnaryOperatorDefinition
                .create('-', Precedence.low(), (ctx)->-ctx.param());

        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
            .append(parentDefinitions, DefaultExpressionOperatorDefinitions.builder()
                .addUnaryOperator(definition)
                .build()
            );

        assertTrue(definitions.hasUnaryOperator('-'));
        assertEquals(definition, definitions.getUnaryOperator('-'));
    }

    @Test
    public void hasConstant() {
        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
            .append(parentDefinitions, DefaultExpressionOperatorDefinitions.builder()
                .addConstant(Constant.newConst("PI",Math.PI))
                .build()
            );

        assertTrue(definitions.hasConstant("PI"));
        assertEquals(Math.PI, definitions.getConstant("PI").getValue(), 0.000000000000001);
    }

    @Test
    public void hasFunctionFromParent() {
        FunctionOperatorDefinition definition = FunctionOperatorDefinition
                .create("sum2(a,b)", (ctx)->ctx.param("a")+ctx.param("b"));

        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
                .append(parentDefinitions,
                        DefaultExpressionOperatorDefinitions.builder()
                                .addFunction(definition)
                                .build()
                );

        assertTrue(definitions.hasFunction("sum"));
        assertEquals(sum, definitions.getFunction("sum"));
    }

    @Test
    public void hasBinaryOperatorFromParent() {
        BinaryOperatorDefinition definition = BinaryOperatorDefinition
                .create('%', Precedence.low(), (ctx)->ctx.param0() + ctx.param1());

        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
                .append(parentDefinitions,DefaultExpressionOperatorDefinitions.builder()
                        .addBinaryOperator(definition)
                        .build()
                );

        assertTrue(definitions.hasBinaryOperator('+'));
        assertEquals(plus, definitions.getBinaryOperator('+'));
    }

    @Test
    public void hasUnaryOperatorFromParent() {
        UnaryOperatorDefinition definition = UnaryOperatorDefinition
                .create('%', Precedence.low(), (ctx)->-ctx.param());

        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
                .append(parentDefinitions, DefaultExpressionOperatorDefinitions.builder()
                        .addUnaryOperator(definition)
                        .build()
                );

        assertTrue(definitions.hasUnaryOperator('-'));
        assertEquals(minus, definitions.getUnaryOperator('-'));
    }

    @Test
    public void hasConstantFromParent() {
        ExpressionOperatorDefinitions definitions = DefaultExpressionOperatorDefinitionsAppender
                .append(parentDefinitions, DefaultExpressionOperatorDefinitions.builder()
                        .addConstant(Constant.newConst("AA",3D))
                        .build()
                );

        assertTrue(definitions.hasConstant("PI"));
        assertEquals(Math.PI, definitions.getConstant("PI").getValue(), 0.000000000000001);
    }

    @Test
    public void requiresParentDefinitions(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Parent instance definition is required");

        DefaultExpressionOperatorDefinitionsAppender.append(null, ExpressionOperatorDefinitions.builder().build());
    }

    @Test
    public void requiresCurrentDefinitions(){
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Current instance definition is required");

        DefaultExpressionOperatorDefinitionsAppender.append(parentDefinitions, null);
    }
}