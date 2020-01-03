/*
 * MIT License
 *
 * Copyright (c) 2020 Cleiton Reis
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

package com.xplusj.parser.tokenizer;

import com.xplusj.ExpressionOperators;
import com.xplusj.operator.Operators;

import java.util.function.Function;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{
    private final Function<String,Boolean> operatorFinder;

    private DefaultExpressionTokenizer(final ExpressionOperators operatorDefinitions) {
        this.operatorFinder = symbol ->
                operatorDefinitions.hasBinaryOperator(symbol)
                || operatorDefinitions.hasUnaryOperator(symbol);
    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.parser.tokenizer.Tokenizer(expression,
                Operators.ALLOWED_OPERATOR_SYMBOLS, operatorFinder);
    }

    public static DefaultExpressionTokenizer create(ExpressionOperators operatorDefinitions){
        return new DefaultExpressionTokenizer(operatorDefinitions);
    }
}
