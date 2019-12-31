package com.xplusj.operator;

import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import java.util.Arrays;

import static com.xplusj.operator.Precedence.*;

public interface Operators {

    /**
     *
     */
    interface Unaries{
        UnaryOperatorDefinition PLUS = UnaryOperatorDefinition.create("+", higherThan(high()), ctx->+ctx.param());
        UnaryOperatorDefinition MIN = UnaryOperatorDefinition.create("-", higherThan(high()), ctx->-ctx.param());
        UnaryOperatorDefinition SQUARE = UnaryOperatorDefinition.create("**", higherThan(PLUS.getPrecedence()), ctx->ctx.param()*ctx.param());
        UnaryOperatorDefinition CUBE = UnaryOperatorDefinition.create("***", sameAs(SQUARE.getPrecedence()), ctx->Math.pow(ctx.param(),3));
        UnaryOperatorDefinition SQRT = UnaryOperatorDefinition.create("#", sameAs(SQUARE.getPrecedence()), ctx->Math.sqrt(ctx.param()));

        //Conditional
        UnaryOperatorDefinition NOT = UnaryOperatorDefinition.create("!", lowerThan(PLUS.getPrecedence()), ctx->ctx.param() == 1 ? 0D : 1D);

        UnaryOperatorDefinition[] OPERATORS = {PLUS,MIN,SQUARE,CUBE,SQRT,NOT};
    }

    /**
     *
     */
    interface Binaries{
        //Math
        BinaryOperatorDefinition ADD = BinaryOperatorDefinition.create("+", low(), ctx->ctx.param0()+ctx.param1());
        BinaryOperatorDefinition SUB = BinaryOperatorDefinition.create("-", low(), ctx->ctx.param0()-ctx.param1());
        BinaryOperatorDefinition MULT = BinaryOperatorDefinition.create("*", high(), ctx->ctx.param0()*ctx.param1());
        BinaryOperatorDefinition DIV = BinaryOperatorDefinition.create("/", high(), ctx->ctx.param0()/ctx.param1());
        BinaryOperatorDefinition MOD = BinaryOperatorDefinition.create("%", high(), ctx->ctx.param0()%ctx.param1());
        BinaryOperatorDefinition POW = BinaryOperatorDefinition.create("^", lowerThan(highest()), ctx->Math.pow(ctx.param0(),ctx.param1()));

        //Conditional
        BinaryOperatorDefinition EQ = BinaryOperatorDefinition.create("==", lowerThan(ADD.getPrecedence()), ctx->ctx.param0() == ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition NE = BinaryOperatorDefinition.create("!=", sameAs(EQ.getPrecedence()), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition GT = BinaryOperatorDefinition.create(">", sameAs(EQ.getPrecedence()), ctx->ctx.param0() > ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition GE = BinaryOperatorDefinition.create(">=", sameAs(EQ.getPrecedence()), ctx->ctx.param0() >= ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition LT = BinaryOperatorDefinition.create("<", sameAs(EQ.getPrecedence()), ctx->ctx.param0() < ctx.param1() ? 1D : 0D);
        BinaryOperatorDefinition LE = BinaryOperatorDefinition.create("<=", sameAs(EQ.getPrecedence()), ctx->ctx.param0() <= ctx.param1() ? 1D : 0D);

        //Logical
        BinaryOperatorDefinition AND = BinaryOperatorDefinition.create("&&", lowerThan(EQ.getPrecedence()), ctx->ctx.param0() == 1 && ctx.param1() == 1 ? 1D : 0D);
        BinaryOperatorDefinition OR = BinaryOperatorDefinition.create("||", sameAs(AND.getPrecedence()), ctx->ctx.param0() == 1 || ctx.param1() == 1 ? 1D : 0D);
        BinaryOperatorDefinition XOR = BinaryOperatorDefinition.create("|", sameAs(AND.getPrecedence()), ctx->ctx.param0() != ctx.param1() ? 1D : 0D);


        BinaryOperatorDefinition[] OPERATORS = {ADD,SUB,MULT,DIV,MOD,POW,EQ,NE,GT,GE,LT,LE,AND,OR,XOR};
    }

    /**
     *
     */
    interface Functions{
        FunctionOperatorDefinition POW = FunctionOperatorDefinition.create("pow(a,b)", ctx->Math.pow(ctx.param(0),ctx.param(1)));
        FunctionOperatorDefinition SQRT = FunctionOperatorDefinition.create("sqrt(a)", ctx->Math.sqrt(ctx.param(0)));
        FunctionOperatorDefinition SUM = FunctionOperatorDefinition.create("sum(a,b,...)", ctx->Arrays.stream(ctx.params()).reduce(0D, Double::sum));
        FunctionOperatorDefinition MAX = FunctionOperatorDefinition.create("max(a,b,...)", ctx->Arrays.stream(ctx.params()).max().orElse(0D));
        FunctionOperatorDefinition MIN = FunctionOperatorDefinition.create("min(a,b,...)", ctx->Arrays.stream(ctx.params()).min().orElse(0D));
        FunctionOperatorDefinition IF = FunctionOperatorDefinition.create("if(condition,true,false)",
                ctx->ctx.param(0) == 1 ? ctx.param(1) : ctx.param(2));

        FunctionOperatorDefinition[] FUNCTIONS = {POW,SQRT,SUM,MAX,MIN,IF};
    }

    /**
     *
     */
    interface Constants{
        Constant PI = Constant.newConst("PI", Math.PI);

        Constant[] CONSTANTS = {PI};
    }
}
