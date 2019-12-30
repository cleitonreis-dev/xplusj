package com.xplusj.expression;

import com.xplusj.ExpressionGlobalContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultExpressionBuiltinOperatorsIntegrationTest {

    private static final double DELTA = 0.0000000000000001;
    private static ExpressionGlobalContext context = ExpressionGlobalContext.builder().build();

    @Test
    public void testSimpleExpression(){
        assertEquals(1.1D, context.expression("1.1").eval(), DELTA);
    }

    @Test
    public void testSimpleExpression1(){
        assertEquals(1D, context.expression("(1)").eval(), DELTA);
    }

    @Test
    public void testUnaryPlus(){
        assertEquals(1D, context.expression("+1").eval(), DELTA);
    }

    @Test
    public void testUnaryMinus(){
        assertEquals(-1D, context.expression("-1").eval(), DELTA);
    }

    @Test
    public void testUnarySquare(){
        assertEquals(2*2D, context.expression("**2").eval(), DELTA);
    }

    @Test
    public void testUnaryCube(){
        assertEquals(Math.pow(2,3), context.expression("***2").eval(), DELTA);
    }

    @Test
    public void testUnaryNot(){
        assertEquals(0D, context.expression("!1").eval(), DELTA);
    }

    @Test
    public void testUnaryNot1(){
        assertEquals(1D, context.expression("!0").eval(), DELTA);
    }

    @Test
    public void testBinaryPlus(){
        assertEquals(2+3D, context.expression("2+3").eval(), DELTA);
    }

    @Test
    public void testBinarySub(){
        assertEquals(2-3D, context.expression("2-3").eval(), DELTA);
    }

    @Test
    public void testBinaryMult(){
        assertEquals(2*3D, context.expression("2*3").eval(), DELTA);
    }

    @Test
    public void testBinaryDiv(){
        assertEquals(2/3D, context.expression("2/3").eval(), DELTA);
    }

    @Test
    public void testBinaryMod(){
        assertEquals(2%3D, context.expression("2%3").eval(), DELTA);
    }

    @Test
    public void testBinaryPow(){
        assertEquals(Math.pow(2,3), context.expression("2^3").eval(), DELTA);
    }

    @Test
    public void testConditionalEQ(){
        assertEquals(1, context.expression("2==2").eval(), DELTA);
    }

    @Test
    public void testConditionalEQ1(){
        assertEquals(0, context.expression("2==1").eval(), DELTA);
    }

    @Test
    public void testConditionalGT(){
        assertEquals(1, context.expression("2>1").eval(), DELTA);
    }

    @Test
    public void testConditionalGT1(){
        assertEquals(0, context.expression("2>2").eval(), DELTA);
    }

    @Test
    public void testConditionalGE(){
        assertEquals(1, context.expression("2>=2").eval(), DELTA);
    }

    @Test
    public void testConditionalGE1(){
        assertEquals(1, context.expression("2>=1").eval(), DELTA);
    }

    @Test
    public void testConditionalGE2(){
        assertEquals(0, context.expression("2>=3").eval(), DELTA);
    }

    @Test
    public void testConditionalLT(){
        assertEquals(1, context.expression("2<3").eval(), DELTA);
    }

    @Test
    public void testConditionalLT1(){
        assertEquals(0, context.expression("2<2").eval(), DELTA);
    }

    @Test
    public void testConditionalLE(){
        assertEquals(1, context.expression("2<=2").eval(), DELTA);
    }

    @Test
    public void testConditionalLE1(){
        assertEquals(1, context.expression("2<=3").eval(), DELTA);
    }

    @Test
    public void testConditionalLE2(){
        assertEquals(0, context.expression("2<=1").eval(), DELTA);
    }

    @Test
    public void testLogicalAnd(){
        assertEquals(1, context.expression("1 && 1").eval(), DELTA);
    }

    @Test
    public void testLogicalAnd1(){
        assertEquals(0, context.expression("1 && 0").eval(), DELTA);
    }

    @Test
    public void testLogicalOr(){
        assertEquals(1, context.expression("0 || 1").eval(), DELTA);
    }

    @Test
    public void testLogicalOr1(){
        assertEquals(0, context.expression("0 || 0").eval(), DELTA);
    }

    @Test
    public void testLogicalXor(){
        assertEquals(1, context.expression("1 | 0").eval(), DELTA);
    }

    @Test
    public void testLogicalXor1(){
        assertEquals(0, context.expression("0 | 0").eval(), DELTA);
    }

    @Test
    public void testLogicalXor2(){
        assertEquals(0, context.expression("1 | 1").eval(), DELTA);
    }

    @Test
    public void testFuncPow(){
        assertEquals(Math.pow(2,4), context.expression("pow(2,4)").eval(), DELTA);
    }

    @Test
    public void testFuncSqrt(){
        assertEquals(Math.sqrt(4), context.expression("sqrt(4)").eval(), DELTA);
    }

    @Test
    public void testFuncMax(){
        assertEquals(Math.max(2,4), context.expression("max(2,4)").eval(), DELTA);
    }

    @Test
    public void testFuncMin(){
        assertEquals(Math.min(2,4), context.expression("min(2,4)").eval(), DELTA);
    }

    @Test
    public void testFuncIf(){
        assertEquals(2D, context.expression("if(1,2,4)").eval(), DELTA);
    }

    @Test
    public void testFuncIf2(){
        assertEquals(4D, context.expression("if(0,2,4)").eval(), DELTA);
    }

    @Test
    public void testConstPI(){
        assertEquals(Math.PI, context.expression("PI").eval(), DELTA);
    }
}