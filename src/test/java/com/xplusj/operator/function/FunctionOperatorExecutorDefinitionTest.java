/*
 * MIT License
 *
 * Copyright (c) 2019 Cleiton Reis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.xplusj.operator.function;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class FunctionOperatorExecutorDefinitionTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void testParamIndexByName(){
        FunctionOperator definition = FunctionOperator.func("func(ab,a,...)", (ctx)->0D);

        assertEquals(0, definition.paramIndex("ab"));
        assertEquals(1, definition.paramIndex("a"));
        assertEquals(2, definition.paramIndex("..."));
    }

    @Test
    public void testParamIndexByName1(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Param c not found for function func. Valid params are: ab,a,...");

        FunctionOperator definition = FunctionOperator.func("func(ab,a,...)", (ctx)->0D);

        assertEquals(0, definition.paramIndex("c"));
    }

    @Test
    public void testFunctionName(){
        FunctionOperator definition = FunctionOperator.func("func(ab,a,...)", (ctx)->0D);
        assertEquals("func", definition.getIdentifier());
    }

    @Test
    public void testParamsLength(){
        FunctionOperator definition = FunctionOperator.func("func(ab,a,...)", (ctx)->0D);
        assertEquals(3, definition.getParamsLength());
    }

    @Test
    public void testParamNames(){
        FunctionOperator definition = FunctionOperator.func("func(ab,a,...)", (ctx)->0D);

        assertEquals("ab", definition.getParams().get(0));
        assertEquals("a", definition.getParams().get(1));
        assertEquals("...", definition.getParams().get(2));
    }

    @Test
    public void testParamVarArgs(){
        FunctionOperator definition = FunctionOperator.func("func(ab,a,...)", (ctx)->0D);
        assertTrue(definition.isVarArgs());
    }

    @Test
    public void testParamVarArgs1(){
        FunctionOperator definition = FunctionOperator.func("func(ab,a)", (ctx)->0D);
        assertFalse(definition.isVarArgs());
    }

    @Test
    public void testValidationAtLeastOneParam(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func()' must have at least one parameter");
        FunctionOperator definition = FunctionOperator.func("func()", (ctx)->0D);
    }

    @Test
    public void testValidationMissingOpeningParenthesis(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func' does not have '('");
        FunctionOperator definition = FunctionOperator.func("func", (ctx)->0D);
    }

    @Test
    public void testValidationMissingClosingParenthesis(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func(' does not have ')'");
        FunctionOperator definition = FunctionOperator.func("func(", (ctx)->0D);
    }

    @Test
    public void testValidationVarArgs(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func(a,...,b)', 'varargs' must be the last parameter");
        FunctionOperator definition = FunctionOperator.func("func(a,...,b)", (ctx)->0D);
    }
}