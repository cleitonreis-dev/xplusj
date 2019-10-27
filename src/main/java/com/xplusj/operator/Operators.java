package com.xplusj.operator;

import static com.xplusj.operator.Precedence.*;

public interface Operators {

    interface Unaries{
        UnaryOperator PLUS = UnaryOperator.create('+', higherThan(high()), ctx->+ctx.getValue());
        UnaryOperator MIN = UnaryOperator.create('-', higherThan(high()), ctx->-ctx.getValue());

        UnaryOperator[] OPERATORS = {PLUS,MIN};
    }

    interface Binaries{
        BinaryOperator ADD = BinaryOperator.create('+', low(), ctx->ctx.getFirstValue()+ctx.getSecondValue());
        BinaryOperator SUB = BinaryOperator.create('-', low(), ctx->ctx.getFirstValue()-ctx.getSecondValue());
        BinaryOperator MULT = BinaryOperator.create('*', high(), ctx->ctx.getFirstValue()*ctx.getSecondValue());
        BinaryOperator DIV = BinaryOperator.create('/', high(), ctx->ctx.getFirstValue()/ctx.getSecondValue());
        BinaryOperator POW = BinaryOperator.create('^', lowerThan(highest()), ctx->Math.pow(ctx.getFirstValue(),ctx.getSecondValue()));

        BinaryOperator[] OPERATORS = {ADD,SUB,MULT,DIV,POW};
    }

    interface Functions{
        FunctionOperator POW = FunctionOperator.create("pow(a,b)", ctx->Math.pow(ctx.param(0),ctx.param(1)));
        FunctionOperator SQRT = FunctionOperator.create("sqrt(a)", ctx->Math.sqrt(ctx.param(0)));
        FunctionOperator MAX = FunctionOperator.create("max(a,b)", ctx->Math.max(ctx.param(0),ctx.param(1)));
        FunctionOperator MIN = FunctionOperator.create("min(a,b)", ctx->Math.min(ctx.param(0),ctx.param(1)));

        FunctionOperator[] FUNCTIONS = {POW,SQRT,MAX,MIN};
    }

    interface Constants{

    }
}
