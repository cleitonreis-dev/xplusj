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

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

@Ignore
public class FunctionIdentifierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFunctionName(){
        FunctionIdentifier identifier = FunctionIdentifier.create("func(ab,a,...)");
        assertEquals("func", identifier.name);
    }

    @Test
    public void testParamsLength(){
        FunctionIdentifier identifier = FunctionIdentifier.create("func(ab,a,...)");
        assertEquals(3, identifier.params.size());
    }

    @Test
    public void testParamNames(){
        FunctionIdentifier identifier = FunctionIdentifier.create("func(ab,a,...)");

        assertEquals("ab", identifier.params.get(0));
        assertEquals("a", identifier.params.get(1));
        assertEquals("...", identifier.params.get(2));
    }

    @Test
    public void testParamVarArgs(){
        FunctionIdentifier identifier = FunctionIdentifier.create("func(ab,a,...)");
        assertTrue(identifier.isVarargs);
    }

    @Test
    public void testParamVarArgs1(){
        FunctionIdentifier identifier = FunctionIdentifier.create("func(ab,a)");
        assertFalse(identifier.isVarargs);
    }

    @Test
    public void testValidationAtLeastOneParam(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func()' must have at least one parameter");
        FunctionIdentifier identifier = FunctionIdentifier.create("func()");
    }

    @Test
    public void testValidationMissingOpeningParenthesis(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func' does not have '('");
        FunctionIdentifier identifier = FunctionIdentifier.create("func");
    }

    @Test
    public void testValidationMissingClosingParenthesis(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func(' does not have ')'");
        FunctionIdentifier identifier = FunctionIdentifier.create("func(");
    }

    @Test
    public void testValidationVarArgs(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Function identifier 'func(a,...,b)', 'varargs' must be the last parameter");
        FunctionIdentifier identifier = FunctionIdentifier.create("func(a,...,b)");
    }
}