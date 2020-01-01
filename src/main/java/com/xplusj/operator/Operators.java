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

package com.xplusj.operator;

import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import static com.xplusj.operator.Precedence.*;
import static com.xplusj.operator.binary.BinaryOperatorDefinition.binary;
import static com.xplusj.operator.unary.UnaryOperatorDefinition.unary;

public interface Operators {

    /**
     *
     */
    interface Unary {
        UnaryOperatorDefinition PLUS = unary("+", higherThan(high()), ctx->+ctx.param());
        UnaryOperatorDefinition MIN = unary("-", higherThan(high()), ctx->-ctx.param());
        UnaryOperatorDefinition SQUARE = unary("**", higherThan(PLUS.getPrecedence()), ctx->ctx.param()*ctx.param());
        UnaryOperatorDefinition CUBE = unary("***", sameAs(SQUARE.getPrecedence()), ctx->Math.pow(ctx.param(), 3));
        UnaryOperatorDefinition SQRT = unary("#", sameAs(SQUARE.getPrecedence()), ctx->Math.sqrt(ctx.param()));

        //Conditional
        UnaryOperatorDefinition NOT = unary("!", lowerThan(PLUS.getPrecedence()), ctx->ctx.param() == 1 ? 0D : 1D);

        UnaryOperatorDefinition[] OPERATORS = {PLUS,MIN,SQUARE,CUBE,SQRT,NOT};
    }

    /**
     *
     */
    interface Binary {
        //Math
        BinaryOperatorDefinition ADD = binary("+", low(), ctx->ctx.param0()+ctx.param1());
        BinaryOperatorDefinition SUB = binary("-", low(), ctx->ctx.param0()-ctx.param1());
        BinaryOperatorDefinition MULT = binary("*", high(), ctx->ctx.param0()*ctx.param1());
        BinaryOperatorDefinition DIV = binary("/", high(), ctx->ctx.param0()/ctx.param1());
        BinaryOperatorDefinition MOD = binary("%", high(), ctx->ctx.param0()%ctx.param1());
        BinaryOperatorDefinition POW = binary("^", lowerThan(highest()), ctx->Math.pow(ctx.param0(),ctx.param1()));

        //Conditional
        BinaryOperatorDefinition EQ = binary("==",
                lowerThan(ADD.getPrecedence()), ctx->ctx.param0() == ctx.param1() ? 1D : 0D);

        BinaryOperatorDefinition NE = binary("!=",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);

        BinaryOperatorDefinition GT = binary(">",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() > ctx.param1() ? 1D : 0D);

        BinaryOperatorDefinition GE = binary(">=",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() >= ctx.param1() ? 1D : 0D);

        BinaryOperatorDefinition LT = binary("<",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() < ctx.param1() ? 1D : 0D);

        BinaryOperatorDefinition LE = binary("<=",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() <= ctx.param1() ? 1D : 0D);

        //Logical
        BinaryOperatorDefinition AND = binary("&&",
                lowerThan(EQ.getPrecedence()), ctx->ctx.param0() == 1 && ctx.param1() == 1 ? 1D : 0D);

        BinaryOperatorDefinition OR = binary("||",
                sameAs(AND.getPrecedence()), ctx->ctx.param0() == 1 || ctx.param1() == 1 ? 1D : 0D);

        BinaryOperatorDefinition XOR = binary("|",
                sameAs(AND.getPrecedence()), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);


        BinaryOperatorDefinition[] OPERATORS = {ADD,SUB,MULT,DIV,MOD,POW,EQ,NE,GT,GE,LT,LE,AND,OR,XOR};
    }
}
