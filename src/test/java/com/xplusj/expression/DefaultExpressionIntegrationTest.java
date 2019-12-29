package com.xplusj.expression;

import com.xplusj.ExpressionGlobalContext;
import com.xplusj.VariableContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultExpressionIntegrationTest {

    private static final double DELTA = 0.0000000000000001;
    private static ExpressionGlobalContext context = ExpressionGlobalContext.builder().build();

    @Before
    public void setUp(){

    }

    @Test
    public void testUnarySquare(){
        assertEquals(2*2D, context.expression("+**2").eval(), DELTA);
    }

    @Test
    public void testUnarySquare2(){
        assertEquals(-(2*2D), context.expression("-**2").eval(), DELTA);
    }

    @Test
    public void testUnaryCube(){
        assertEquals(Math.pow(2,3), context.expression("+***2").eval(), DELTA);
    }

    @Test
    public void testUnaryCube2(){
        assertEquals(-Math.pow(2,3), context.expression("-***2").eval(), DELTA);
    }

    @Test
    public void testConditionalIf(){
        assertEquals(34, context.expression("if(2==2, a, PI)").eval(VariableContext.builder().add("a", 34D).build()), DELTA);
    }

    @Test
    public void testConditionalIf2(){
        assertEquals(Math.PI, context.expression("if(2==a, a, PI)").eval(VariableContext.builder().add("a", 3D).build()), DELTA);
    }

    @Test
    public void testConditionalIf3(){
        assertEquals(2D, context.expression("if(2>=a, a, PI)").eval(VariableContext.builder().add("a", 2D).build()), DELTA);
    }
}