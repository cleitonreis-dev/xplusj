package com.xplusj.expression;

import com.xplusj.VariableContext;
import com.xplusj.context.DefaultVariableContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FormulaTests {

    @Test
    public void testPlus(){
        double result = eval("a+a").eval(vars().add("a",1).build());
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusAndMinus(){
        double result = eval("a+b-a").eval(vars()
                .add("a", 3)
                .add("b", 1D)
                .build()
        );
        assertEquals(1D, result, 0);
    }

    @Test
    public void testCallingFunction(){
        double result = eval("max(3,x)").eval(vars().add("x",3).build());
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyWithFunctionCall(){
        double result = eval("ab*max(3,ab)").eval(vars().add("ab",2).build());
        assertEquals(6D, result, 0);
    }

    private static FormulaExpression eval(String expression){
        return FormulaExpression.create(expression, InlineExpressionTests.ENV);
    }

    private static VariableContext.Builder vars(){
        return DefaultVariableContext.builder();
    }
}