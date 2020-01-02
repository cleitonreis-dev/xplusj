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

import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.unary.UnaryOperator;

import java.util.HashSet;
import java.util.Set;

import static com.xplusj.operator.Precedence.*;
import static com.xplusj.operator.binary.BinaryOperator.binary;
import static com.xplusj.operator.unary.UnaryOperator.unary;

public interface Operators {

    /**
     *
     */
    Set<Character> ALLOWED_OPERATOR_SYMBOLS = new HashSet<Character>(){{
        add('!');add('@');add('#');add('$');
        add('%');add('"');add('\'');add('&');
        add('*');add('-');add('+');add('=');
        add(':');add(';');add('~');add('^');
        add('?');add('|');add('\\');add('>');
        add('<');add('÷');add('/');add('ƒ');
        add('£');add('¿');add('«');add('»');
        add('¢');add('§');add('√');add('∛');
    }};

    /**
     *
     */
    interface Unary {
        UnaryOperator PLUS = unary("+", higherThan(high()), ctx->+ctx.param());
        UnaryOperator MIN = unary("-", higherThan(high()), ctx->-ctx.param());
        UnaryOperator SQUARE = unary("**", higherThan(PLUS.getPrecedence()), ctx->ctx.param()*ctx.param());
        UnaryOperator CUBE = unary("***", sameAs(SQUARE.getPrecedence()), ctx->Math.pow(ctx.param(), 3));
        UnaryOperator SQRT = unary("#", sameAs(SQUARE.getPrecedence()), ctx->Math.sqrt(ctx.param()));

        //Conditional
        UnaryOperator NOT = unary("!", lowerThan(PLUS.getPrecedence()), ctx->ctx.param() == 1 ? 0D : 1D);

        UnaryOperator[] OPERATORS = {PLUS,MIN,SQUARE,CUBE,SQRT,NOT};
    }

    /**
     *
     */
    interface Binary {
        //Math
        BinaryOperator ADD = binary("+", low(), ctx->ctx.param0()+ctx.param1());
        BinaryOperator SUB = binary("-", low(), ctx->ctx.param0()-ctx.param1());
        BinaryOperator MULT = binary("*", high(), ctx->ctx.param0()*ctx.param1());
        BinaryOperator DIV = binary("/", high(), ctx->ctx.param0()/ctx.param1());
        BinaryOperator MOD = binary("%", high(), ctx->ctx.param0()%ctx.param1());
        BinaryOperator POW = binary("^", lowerThan(highest()), ctx->Math.pow(ctx.param0(),ctx.param1()));

        //Conditional
        BinaryOperator EQ = binary("==",
                lowerThan(ADD.getPrecedence()), ctx->ctx.param0() == ctx.param1() ? 1D : 0D);

        BinaryOperator NE = binary("!=",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);

        BinaryOperator GT = binary(">",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() > ctx.param1() ? 1D : 0D);

        BinaryOperator GE = binary(">=",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() >= ctx.param1() ? 1D : 0D);

        BinaryOperator LT = binary("<",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() < ctx.param1() ? 1D : 0D);

        BinaryOperator LE = binary("<=",
                sameAs(EQ.getPrecedence()), ctx->ctx.param0() <= ctx.param1() ? 1D : 0D);

        //Logical
        BinaryOperator AND = binary("&&",
                lowerThan(EQ.getPrecedence()), ctx->ctx.param0() == 1 && ctx.param1() == 1 ? 1D : 0D);

        BinaryOperator OR = binary("||",
                sameAs(AND.getPrecedence()), ctx->ctx.param0() == 1 || ctx.param1() == 1 ? 1D : 0D);

        BinaryOperator XOR = binary("|",
                sameAs(AND.getPrecedence()), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);


        BinaryOperator[] OPERATORS = {ADD,SUB,MULT,DIV,MOD,POW,EQ,NE,GT,GE,LT,LE,AND,OR,XOR};
    }
}
