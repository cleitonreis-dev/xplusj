package com.xplusj;

import com.xplusj.stack.Stack;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class StackTests {

    @Test
    public void testPushAndPull(){
        Stack<Integer> stack = new Stack<>();

        assertTrue(stack.isEmpty());

        IntStream.range(0, 10).forEach(stack::push);
        int i = 9;
        while(!stack.isEmpty()){
            assertEquals(i--, stack.pull().intValue());
        }

        assertTrue(stack.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyStack(){
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.pull();
        stack.pull();
    }
}