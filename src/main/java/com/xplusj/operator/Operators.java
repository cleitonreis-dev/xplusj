package com.xplusj.operator;

import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import static com.xplusj.operator.Precedence.*;

public interface Operators {

    interface Unaries{
        UnaryOperatorDefinition PLUS = UnaryOperatorDefinition.create('+', higherThan(high()), ctx->+ctx.getValue());
        UnaryOperatorDefinition MIN = UnaryOperatorDefinition.create('-', higherThan(high()), ctx->-ctx.getValue());

        UnaryOperatorDefinition[] OPERATORS = {PLUS,MIN};
    }

    interface Binaries{
        BinaryOperatorDefinition ADD = BinaryOperatorDefinition.create('+', low(), ctx->ctx.getFirstValue()+ctx.getSecondValue());
        BinaryOperatorDefinition SUB = BinaryOperatorDefinition.create('-', low(), ctx->ctx.getFirstValue()-ctx.getSecondValue());
        BinaryOperatorDefinition MULT = BinaryOperatorDefinition.create('*', high(), ctx->ctx.getFirstValue()*ctx.getSecondValue());
        BinaryOperatorDefinition DIV = BinaryOperatorDefinition.create('/', high(), ctx->ctx.getFirstValue()/ctx.getSecondValue());
        BinaryOperatorDefinition POW = BinaryOperatorDefinition.create('^', lowerThan(highest()), ctx->Math.pow(ctx.getFirstValue(),ctx.getSecondValue()));

        BinaryOperatorDefinition[] OPERATORS = {ADD,SUB,MULT,DIV,POW};
    }

    interface Functions{
        FunctionOperatorDefinition POW = FunctionOperatorDefinition.create("pow(a,b)", ctx->Math.pow(ctx.param(0),ctx.param(1)));
        FunctionOperatorDefinition SQRT = FunctionOperatorDefinition.create("sqrt(a)", ctx->Math.sqrt(ctx.param(0)));
        FunctionOperatorDefinition MAX = FunctionOperatorDefinition.create("max(a,b)", ctx->Math.max(ctx.param(0),ctx.param(1)));
        FunctionOperatorDefinition MIN = FunctionOperatorDefinition.create("min(a,b)", ctx->Math.min(ctx.param(0),ctx.param(1)));

        FunctionOperatorDefinition[] FUNCTIONS = {POW,SQRT,MAX,MIN};
    }

    interface Constants{

    }
}
