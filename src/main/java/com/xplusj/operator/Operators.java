package com.xplusj.operator;

import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import static com.xplusj.operator.Precedence.*;

public interface Operators {

    interface Unaries{
        UnaryOperatorDefinition PLUS = UnaryOperatorDefinition.create("+", higherThan(high()), ctx->+ctx.param());
        UnaryOperatorDefinition MIN = UnaryOperatorDefinition.create("-", higherThan(high()), ctx->-ctx.param());
        UnaryOperatorDefinition SQUARE = UnaryOperatorDefinition.create("**", higherThan(high()), ctx->ctx.param()*ctx.param());
        UnaryOperatorDefinition CUBE = UnaryOperatorDefinition.create("***", higherThan(high()), ctx->Math.pow(ctx.param(), 3));

        UnaryOperatorDefinition[] OPERATORS = {PLUS,MIN,SQUARE,CUBE};
    }

    interface Binaries{
        //Math
        BinaryOperatorDefinition ADD = BinaryOperatorDefinition.create("+", low(), ctx->ctx.param0()+ctx.param1());
        BinaryOperatorDefinition SUB = BinaryOperatorDefinition.create("-", low(), ctx->ctx.param0()-ctx.param1());
        BinaryOperatorDefinition MULT = BinaryOperatorDefinition.create("*", high(), ctx->ctx.param0()*ctx.param1());
        BinaryOperatorDefinition DIV = BinaryOperatorDefinition.create("/", high(), ctx->ctx.param0()/ctx.param1());
        BinaryOperatorDefinition MOD = BinaryOperatorDefinition.create("%", high(), ctx->ctx.param0()%ctx.param1());
        BinaryOperatorDefinition POW = BinaryOperatorDefinition.create("^", lowerThan(highest()), ctx->Math.pow(ctx.param0(),ctx.param1()));

        //Conditional
        BinaryOperatorDefinition EQ = BinaryOperatorDefinition.create("==", low(), ctx->ctx.param0() == ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition GT = BinaryOperatorDefinition.create(">", low(), ctx->ctx.param0() > ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition GE = BinaryOperatorDefinition.create(">=", low(), ctx->ctx.param0() >= ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition LT = BinaryOperatorDefinition.create("<", low(), ctx->ctx.param0() < ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition LE = BinaryOperatorDefinition.create("<=", low(), ctx->ctx.param0() <= ctx.param1() ? 1D : 0D);

        //Logical
        BinaryOperatorDefinition AND = BinaryOperatorDefinition.create("&&", low(), ctx->ctx.param0() == 1 && ctx.param1() == 1 ? 1D : 0D);
        BinaryOperatorDefinition OR = BinaryOperatorDefinition.create("||", low(), ctx->ctx.param0() == 1 || ctx.param1() == 1 ? 1D : 0D);
        BinaryOperatorDefinition XOR = BinaryOperatorDefinition.create("|", low(), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);


        BinaryOperatorDefinition[] OPERATORS = {ADD,SUB,MULT,DIV,MOD,POW,EQ,GT,GE,LT,LE,AND,OR,XOR};
    }

    interface Functions{
        FunctionOperatorDefinition POW = FunctionOperatorDefinition.create("pow(a,b)", ctx->Math.pow(ctx.param(0),ctx.param(1)));
        FunctionOperatorDefinition SQRT = FunctionOperatorDefinition.create("sqrt(a)", ctx->Math.sqrt(ctx.param(0)));
        FunctionOperatorDefinition MAX = FunctionOperatorDefinition.create("max(a,b)", ctx->Math.max(ctx.param(0),ctx.param(1)));
        FunctionOperatorDefinition MIN = FunctionOperatorDefinition.create("min(a,b)", ctx->Math.min(ctx.param(0),ctx.param(1)));
        FunctionOperatorDefinition IF = FunctionOperatorDefinition.create("if(condition,true,false)",
                ctx->ctx.param(0) == 1 ? ctx.param(1) : ctx.param(2));

        FunctionOperatorDefinition[] FUNCTIONS = {POW,SQRT,MAX,MIN,IF};
    }

    interface Constants{
        Constant PI = Constant.newConst("PI", Math.PI);

        Constant[] CONSTANTS = {PI};
    }
}
