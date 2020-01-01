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

import com.xplusj.operator.function.FunctionOperatorDefinition;

import java.util.Arrays;

public interface Functions {
    FunctionOperatorDefinition IF = FunctionOperatorDefinition.create("if(condition,true,false)",
            ctx->ctx.param(0) == 1 ? ctx.param(1) : ctx.param(2));

    FunctionOperatorDefinition POW = FunctionOperatorDefinition.create("pow(a,b)",
            ctx->Math.pow(ctx.param(0),ctx.param(1)));

    FunctionOperatorDefinition SQRT = FunctionOperatorDefinition.create("sqrt(a)",
            ctx->Math.sqrt(ctx.param(0)));

    FunctionOperatorDefinition SUM = FunctionOperatorDefinition.create("sum(a,b,...)",
            ctx-> Arrays.stream(ctx.params()).reduce(0D, Double::sum));

    FunctionOperatorDefinition MAX = FunctionOperatorDefinition.create("max(a,b,...)",
            ctx->Arrays.stream(ctx.params()).max().orElse(0D));

    FunctionOperatorDefinition MIN = FunctionOperatorDefinition.create("min(a,b,...)",
            ctx->Arrays.stream(ctx.params()).min().orElse(0D));

    FunctionOperatorDefinition ABS = FunctionOperatorDefinition.create("abs(a)",
            ctx->Math.abs(ctx.param(0)));

    FunctionOperatorDefinition SIN = FunctionOperatorDefinition.create("sin(a)",
            ctx->Math.sin(ctx.param(0)));

    FunctionOperatorDefinition COS = FunctionOperatorDefinition.create("cos(a)",
            ctx->Math.cos(ctx.param(0)));

    FunctionOperatorDefinition TAN = FunctionOperatorDefinition.create("tan(a)",
            ctx->Math.tan(ctx.param(0)));

    FunctionOperatorDefinition COT = FunctionOperatorDefinition.create("cot(a)",
            ctx->1D/Math.tan(ctx.param(0)));

    FunctionOperatorDefinition ASIN = FunctionOperatorDefinition.create("asin(a)",
            ctx->Math.asin(ctx.param(0)));

    FunctionOperatorDefinition ACOS = FunctionOperatorDefinition.create("acos(a)",
            ctx->Math.acos(ctx.param(0)));

    FunctionOperatorDefinition ATAN = FunctionOperatorDefinition.create("atan(a)",
            ctx->Math.atan(ctx.param(0)));

    FunctionOperatorDefinition SINH = FunctionOperatorDefinition.create("sinh(a)",
            ctx->Math.sinh(ctx.param(0)));

    FunctionOperatorDefinition COSH = FunctionOperatorDefinition.create("cosh(a)",
            ctx->Math.cosh(ctx.param(0)));

    FunctionOperatorDefinition TANH = FunctionOperatorDefinition.create("tanh(a)",
            ctx->Math.tanh(ctx.param(0)));

    FunctionOperatorDefinition LOG = FunctionOperatorDefinition.create("log(a)",
            ctx->Math.log(ctx.param(0)));

    FunctionOperatorDefinition LOG10 = FunctionOperatorDefinition.create("log10(a)",
            ctx->Math.log10(ctx.param(0)));

    FunctionOperatorDefinition LOG2 = FunctionOperatorDefinition.create("log2(a)",
            ctx->Math.log(ctx.param(0))/Math.log(2D));

    FunctionOperatorDefinition LOG1P = FunctionOperatorDefinition.create("log1p(a)",
            ctx->Math.log1p(ctx.param(0)));

    FunctionOperatorDefinition CEIL = FunctionOperatorDefinition.create("ceil(a)",
            ctx->Math.ceil(ctx.param(0)));

    FunctionOperatorDefinition FLOOR = FunctionOperatorDefinition.create("floor(a)",
            ctx->Math.floor(ctx.param(0)));

    FunctionOperatorDefinition CBRT = FunctionOperatorDefinition.create("cbrt(a)",
            ctx->Math.cbrt(ctx.param(0)));

    FunctionOperatorDefinition EXP = FunctionOperatorDefinition.create("exp(a)",
            ctx->Math.exp(ctx.param(0)));

    FunctionOperatorDefinition EXPM1 = FunctionOperatorDefinition.create("expm1(a)",
            ctx->Math.expm1(ctx.param(0)));

    FunctionOperatorDefinition SIGNUM = FunctionOperatorDefinition.create("signum(a)",
            ctx->Math.signum(ctx.param(0)));

    FunctionOperatorDefinition CSC = FunctionOperatorDefinition.create("csc(a)",
            ctx->1D/Math.sin(ctx.param(0)));

    FunctionOperatorDefinition SEC = FunctionOperatorDefinition.create("sec(a)",
            ctx->1D/Math.cos(ctx.param(0)));

    FunctionOperatorDefinition CSCH = FunctionOperatorDefinition.create("csch(a)",
            ctx->1D/Math.sinh(ctx.param(0)));

    FunctionOperatorDefinition SECH = FunctionOperatorDefinition.create("sech(a)",
            ctx->1D/Math.cosh(ctx.param(0)));

    FunctionOperatorDefinition COTH = FunctionOperatorDefinition.create("coth(a)",
            ctx->Math.cosh(ctx.param(0))/Math.sinh(ctx.param(0)));

    FunctionOperatorDefinition TO_RADIANS = FunctionOperatorDefinition.create("to_radians(a)",
            ctx->Math.toRadians(ctx.param(0)));

    FunctionOperatorDefinition TO_DEGREES = FunctionOperatorDefinition.create("to_degrees(a)",
            ctx->Math.toDegrees(ctx.param(0)));

    FunctionOperatorDefinition[] FUNCTIONS = {
            IF,POW,SQRT,SUM,MAX,MIN,ABS,SIN,COS,TAN,COT,
            ASIN,ACOS,ATAN,SINH,COSH,TANH,LOG,LOG10,LOG2,
            LOG1P,CEIL,FLOOR,CBRT,EXP,EXPM1, SIGNUM,CSC,
            SEC,CSCH,SECH,COTH,TO_RADIANS,TO_DEGREES
    };
}
