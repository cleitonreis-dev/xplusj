package com.xplusj.operator;

import com.xplusj.ExpressionContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuiltinOperatorsIntegrationTest {

    private static final double DELTA = 0.0000000000000001;
    private static ExpressionContext context = ExpressionContext.builder().build();

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
    public void testFuncAbs(){
        double param = 93D;
        assertEquals(Math.abs(param), context.getFunction("abs").execute(param), DELTA);
    }

    @Test
    public void testFuncSin(){
        double param = 93D;
        assertEquals(Math.sin(param), context.getFunction("sin").execute(param), DELTA);
    }

    @Test
    public void testFuncCos(){
        double param = 93D;
        assertEquals(Math.cos(param), context.getFunction("cos").execute(param), DELTA);
    }

    @Test
    public void testFuncTan(){
        double param = 93D;
        assertEquals(Math.tan(param), context.getFunction("tan").execute(param), DELTA);
    }

    @Test
    public void testFuncCot(){
        double param = 93D;
        assertEquals(1D/Math.tan(param), context.getFunction("cot").execute(param), DELTA);
    }

    @Test
    public void testFuncAsin(){
        double param = 93D;
        assertEquals(Math.asin(param), context.getFunction("asin").execute(param), DELTA);
    }

    @Test
    public void testFuncAcos(){
        double param = 93D;
        assertEquals(Math.acos(param), context.getFunction("acos").execute(param), DELTA);
    }

    @Test
    public void testFuncAtan(){
        double param = 93D;
        assertEquals(Math.atan(param), context.getFunction("atan").execute(param), DELTA);
    }

    @Test
    public void testFuncSinh(){
        double param = 93D;
        assertEquals(Math.sinh(param), context.getFunction("sinh").execute(param), DELTA);
    }

    @Test
    public void testFuncCosh(){
        double param = 93D;
        assertEquals(Math.cosh(param), context.getFunction("cosh").execute(param), DELTA);
    }

    @Test
    public void testFuncTanh(){
        double param = 93D;
        assertEquals(Math.tanh(param), context.getFunction("tanh").execute(param), DELTA);
    }

    @Test
    public void testFuncLog(){
        double param = 93D;
        assertEquals(Math.log(param), context.getFunction("log").execute(param), DELTA);
    }

    @Test
    public void testFuncLog10(){
        double param = 93D;
        assertEquals(Math.log10(param), context.getFunction("log10").execute(param), DELTA);
    }

    @Test
    public void testFuncLog2(){
        double param = 93D;
        assertEquals(Math.log(param)/Math.log(2D), context.getFunction("log2").execute(param), DELTA);
    }

    @Test
    public void testFuncLog1p(){
        double param = 93D;
        assertEquals(Math.log1p(param), context.getFunction("log1p").execute(param), DELTA);
    }

    @Test
    public void testFuncCeil(){
        double param = 93.5D;
        assertEquals(Math.ceil(param), context.getFunction("ceil").execute(param), DELTA);
    }

    @Test
    public void testFuncFloor(){
        double param = 93.5D;
        assertEquals(Math.floor(param), context.getFunction("floor").execute(param), DELTA);
    }

    @Test
    public void testFuncCbrt(){
        double param = 93D;
        assertEquals(Math.cbrt(param), context.getFunction("cbrt").execute(param), DELTA);
    }

    @Test
    public void testFuncExp(){
        double param = 93D;
        assertEquals(Math.exp(param), context.getFunction("exp").execute(param), DELTA);
    }

    @Test
    public void testFuncExpm1(){
        double param = 93D;
        assertEquals(Math.expm1(param), context.getFunction("expm1").execute(param), DELTA);
    }

    @Test
    public void testFuncSignum(){
        double param = 93D;
        assertEquals(Math.signum(param), context.getFunction("signum").execute(param), DELTA);
    }

    @Test
    public void testFuncCsc(){
        double param = 93D;
        assertEquals(1/Math.sin(param), context.getFunction("csc").execute(param), DELTA);
    }

    @Test
    public void testFuncSec(){
        double param = 93D;
        assertEquals(1/Math.cos(param), context.getFunction("sec").execute(param), DELTA);
    }

    @Test
    public void testFuncCsch(){
        double param = 93D;
        assertEquals(1/Math.sinh(param), context.getFunction("csch").execute(param), DELTA);
    }

    @Test
    public void testFuncSech(){
        double param = 93D;
        assertEquals(1/Math.cosh(param), context.getFunction("sech").execute(param), DELTA);
    }

    @Test
    public void testFuncCoth(){
        double param = 93D;
        assertEquals(Math.cosh(param)/Math.sinh(param), context.getFunction("coth").execute(param), DELTA);
    }

    @Test
    public void testFuncToRadians(){
        double param = 93D;
        assertEquals(Math.toRadians(param), context.getFunction("to_radians").execute(param), DELTA);
    }

    @Test
    public void testFuncToDegrees(){
        double param = 93D;
        assertEquals(Math.toDegrees(param), context.getFunction("to_degrees").execute(param), DELTA);
    }

    @Test
    public void testConstPI(){
        assertEquals(Math.PI, context.getDefinitions().getConstant("PI").getValue(), DELTA);
    }
}