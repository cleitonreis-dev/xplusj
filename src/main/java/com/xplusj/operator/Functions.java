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

import com.xplusj.operator.function.FunctionOperator;

import java.util.Arrays;

import static com.xplusj.operator.function.FunctionOperator.func;

public interface Functions {
    FunctionOperator IF = func("if(condition,true,false)", ctx->ctx.param(0) == 1 ? ctx.param(1) : ctx.param(2));
    FunctionOperator POW = func("pow(a,b)", ctx->Math.pow(ctx.param(0),ctx.param(1)));
    FunctionOperator SQRT = func("sqrt(a)", ctx->Math.sqrt(ctx.param(0)));
    FunctionOperator SUM = func("sum(a,b,...)", ctx-> Arrays.stream(ctx.params()).reduce(0D, Double::sum));
    FunctionOperator MAX = func("max(a,b,...)", ctx->Arrays.stream(ctx.params()).max().orElse(0D));
    FunctionOperator MIN = func("min(a,b,...)", ctx->Arrays.stream(ctx.params()).min().orElse(0D));
    FunctionOperator ABS = func("abs(a)", ctx->Math.abs(ctx.param(0)));
    FunctionOperator SIN = func("sin(a)", ctx->Math.sin(ctx.param(0)));
    FunctionOperator COS = func("cos(a)", ctx->Math.cos(ctx.param(0)));
    FunctionOperator TAN = func("tan(a)", ctx->Math.tan(ctx.param(0)));
    FunctionOperator COT = func("cot(a)", ctx->1D/Math.tan(ctx.param(0)));
    FunctionOperator ASIN = func("asin(a)", ctx->Math.asin(ctx.param(0)));
    FunctionOperator ACOS = func("acos(a)", ctx->Math.acos(ctx.param(0)));
    FunctionOperator ATAN = func("atan(a)", ctx->Math.atan(ctx.param(0)));
    FunctionOperator SINH = func("sinh(a)", ctx->Math.sinh(ctx.param(0)));
    FunctionOperator COSH = func("cosh(a)", ctx->Math.cosh(ctx.param(0)));
    FunctionOperator TANH = func("tanh(a)", ctx->Math.tanh(ctx.param(0)));
    FunctionOperator LOG = func("log(a)", ctx->Math.log(ctx.param(0)));
    FunctionOperator LOG10 = func("log10(a)", ctx->Math.log10(ctx.param(0)));
    FunctionOperator LOG2 = func("log2(a)", ctx->Math.log(ctx.param(0))/Math.log(2D));
    FunctionOperator LOG1P = func("log1p(a)", ctx->Math.log1p(ctx.param(0)));
    FunctionOperator CEIL = func("ceil(a)", ctx->Math.ceil(ctx.param(0)));
    FunctionOperator FLOOR = func("floor(a)", ctx->Math.floor(ctx.param(0)));
    FunctionOperator CBRT = func("cbrt(a)", ctx->Math.cbrt(ctx.param(0)));
    FunctionOperator EXP = func("exp(a)", ctx->Math.exp(ctx.param(0)));
    FunctionOperator EXPM1 = func("expm1(a)", ctx->Math.expm1(ctx.param(0)));
    FunctionOperator SIGNUM = func("signum(a)", ctx->Math.signum(ctx.param(0)));
    FunctionOperator CSC = func("csc(a)", ctx->1D/Math.sin(ctx.param(0)));
    FunctionOperator SEC = func("sec(a)", ctx->1D/Math.cos(ctx.param(0)));
    FunctionOperator CSCH = func("csch(a)", ctx->1D/Math.sinh(ctx.param(0)));
    FunctionOperator SECH = func("sech(a)", ctx->1D/Math.cosh(ctx.param(0)));
    FunctionOperator COTH = func("coth(a)", ctx->Math.cosh(ctx.param(0))/Math.sinh(ctx.param(0)));
    FunctionOperator TO_RADIANS = func("to_radians(a)", ctx->Math.toRadians(ctx.param(0)));
    FunctionOperator TO_DEGREES = func("to_degrees(a)", ctx->Math.toDegrees(ctx.param(0)));

    FunctionOperator[] FUNCTIONS = {
            IF,POW,SQRT,SUM,MAX,MIN,ABS,SIN,COS,TAN,COT,
            ASIN,ACOS,ATAN,SINH,COSH,TANH,LOG,LOG10,LOG2,
            LOG1P,CEIL,FLOOR,CBRT,EXP,EXPM1, SIGNUM,CSC,
            SEC,CSCH,SECH,COTH,TO_RADIANS,TO_DEGREES
    };
}
