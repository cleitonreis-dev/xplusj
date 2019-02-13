package com.xplusj.expression;

import com.xplusj.Environment;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InlineExpressionTests {

    private Environment env = Environment.defaultEnv().build();

    @Test
    public void testPlus(){
        double result = env.expression("1+1").eval();
        assertEquals(2D, result, 0);
    }
}