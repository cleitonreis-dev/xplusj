package com.xplusj.operator;

import com.xplusj.ExpressionGlobalContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuiltinOperatorsIntegrationTest {

    private static final double DELTA = 0.0000000000000001;
    private static ExpressionGlobalContext context = ExpressionGlobalContext.builder().build();

    @Test
    public void testUnaryPlus(){
        assertEquals(1D, context.getUnaryOperator("+").execute(1), DELTA);
    }

    @Test
    public void testUnaryMinus(){
        assertEquals(-1D, context.getUnaryOperator("-").execute(1), DELTA);
    }

    @Test
    public void testUnarySquare(){
        assertEquals(2*2D, context.getUnaryOperator("**").execute(2), DELTA);
    }

    @Test
    public void testUnaryCube(){
        assertEquals(Math.pow(2,3), context.getUnaryOperator("***").execute(2), DELTA);
    }

    @Test
    public void testUnaryNot(){
        assertEquals(1, context.getUnaryOperator("!").execute(0), DELTA);
    }

    @Test
    public void testUnaryNot1(){
        assertEquals(0, context.getUnaryOperator("!").execute(1), DELTA);
    }

    @Test
    public void testBinaryPlus(){
        assertEquals(2+3D, context.getBinaryOperator("+").execute(2,3), DELTA);
    }

    @Test
    public void testBinarySub(){
        assertEquals(2-3D, context.getBinaryOperator("-").execute(2,3), DELTA);
    }

    @Test
    public void testBinaryMult(){
        assertEquals(2*3D, context.getBinaryOperator("*").execute(2,3), DELTA);
    }

    @Test
    public void testBinaryDiv(){
        assertEquals(2/3D, context.getBinaryOperator("/").execute(2,3), DELTA);
    }

    @Test
    public void testBinaryMod(){
        assertEquals(2%3D, context.getBinaryOperator("%").execute(2,3), DELTA);
    }

    @Test
    public void testBinaryPow(){
        assertEquals(Math.pow(2,3), context.getBinaryOperator("^").execute(2,3), DELTA);
    }

    @Test
    public void testConditionalEQ(){
        assertEquals(1, context.getBinaryOperator("==").execute(2,2), DELTA);
    }

    @Test
    public void testConditionalEQ1(){
        assertEquals(0, context.getBinaryOperator("==").execute(2,1), DELTA);
    }

    @Test
    public void testConditionalNE(){
        assertEquals(1, context.getBinaryOperator("!=").execute(2,1), DELTA);
    }

    @Test
    public void testConditionalNE1(){
        assertEquals(0, context.getBinaryOperator("!=").execute(2,2), DELTA);
    }

    @Test
    public void testConditionalGT(){
        assertEquals(1, context.getBinaryOperator(">").execute(2,1), DELTA);
    }

    @Test
    public void testConditionalGT1(){
        assertEquals(0, context.getBinaryOperator(">").execute(2,2), DELTA);
    }

    @Test
    public void testConditionalGE(){
        assertEquals(1, context.getBinaryOperator(">=").execute(2,2), DELTA);
    }

    @Test
    public void testConditionalGE1(){
        assertEquals(1, context.getBinaryOperator(">=").execute(2,1), DELTA);
    }

    @Test
    public void testConditionalGE2(){
        assertEquals(0, context.getBinaryOperator(">=").execute(2,3), DELTA);
    }

    @Test
    public void testConditionalLT(){
        assertEquals(1, context.getBinaryOperator("<").execute(2,3), DELTA);
    }

    @Test
    public void testConditionalLT1(){
        assertEquals(0, context.getBinaryOperator("<").execute(2,2), DELTA);
    }

    @Test
    public void testConditionalLE(){
        assertEquals(1, context.getBinaryOperator("<=").execute(2,2), DELTA);
    }

    @Test
    public void testConditionalLE1(){
        assertEquals(1, context.getBinaryOperator("<=").execute(2,3), DELTA);
    }

    @Test
    public void testConditionalLE2(){
        assertEquals(0, context.getBinaryOperator("<=").execute(2,1), DELTA);
    }

    @Test
    public void testLogicalAnd(){
        assertEquals(1, context.getBinaryOperator("&&").execute(1,1), DELTA);
    }

    @Test
    public void testLogicalAnd1(){
        assertEquals(0, context.getBinaryOperator("&&").execute(1,0), DELTA);
    }

    @Test
    public void testLogicalOr(){
        assertEquals(1, context.getBinaryOperator("||").execute(0,1), DELTA);
    }

    @Test
    public void testLogicalOr1(){
        assertEquals(0, context.getBinaryOperator("||").execute(0,0), DELTA);
    }

    @Test
    public void testLogicalXor(){
        assertEquals(1, context.getBinaryOperator("|").execute(1,0), DELTA);
    }

    @Test
    public void testLogicalXor1(){
        assertEquals(0, context.getBinaryOperator("|").execute(0,0), DELTA);
    }

    @Test
    public void testLogicalXor2(){
        assertEquals(0, context.getBinaryOperator("|").execute(1,1), DELTA);
    }

    @Test
    public void testFuncPow(){
        assertEquals(Math.pow(2,4), context.getFunction("pow").execute(2,4), DELTA);
    }

    @Test
    public void testFuncSqrt(){
        assertEquals(Math.sqrt(4), context.getFunction("sqrt").execute(4), DELTA);
    }

    @Test
    public void testFuncSum(){
        assertEquals(2+4, context.getFunction("sum").execute(2,4), DELTA);
    }

    @Test
    public void testFuncMax(){
        assertEquals(Math.max(2,4), context.getFunction("max").execute(2,4), DELTA);
    }

    @Test
    public void testFuncMin(){
        assertEquals(Math.min(2,4), context.getFunction("min").execute(2,4), DELTA);
    }

    @Test
    public void testFuncIf(){
        assertEquals(2D, context.getFunction("if").execute(1,2,4), DELTA);
    }

    @Test
    public void testFuncIf2(){
        assertEquals(4D, context.getFunction("if").execute(0,2,4), DELTA);
    }

    @Test
    public void testConstPI(){
        assertEquals(Math.PI, context.getDefinitions().getConstant("PI").getValue(), DELTA);
    }
}