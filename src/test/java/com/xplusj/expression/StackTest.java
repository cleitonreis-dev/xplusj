package com.xplusj.expression;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class StackTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPushAndPull(){
        Stack<Integer> stack = Stack.instance();

        assertTrue(stack.isEmpty());

        IntStream.range(0, 10).forEach(stack::push);
        int i = 9;
        while(!stack.isEmpty()){
            assertEquals(i--, stack.pull().intValue());
        }

        assertTrue(stack.isEmpty());
    }

    @Test
    public void testEmptyStack(){
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Empty stack");

        Stack<Integer> stack = Stack.instance();

        stack.push(1);
        stack.pull();
        stack.pull();
    }

    @Test
    public void testEmptyStack2(){
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Empty stack");
        Stack.instance().peek();
    }


}