package com.xplusj.operator;

import java.util.function.Function;

public class UnaryOperator extends UBOperator<UnaryOperatorContext>{

    public UnaryOperator(char symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        super(OperatorType.UNARY, symbol, precedence, function);
    }

    @Override
    public int getParamsLength() {
        return 1;
    }

    @Override
    public double execute(UnaryOperatorContext context) {
        return function.apply(context);
    }

    public static UnaryOperator create(char symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        return new UnaryOperator(symbol,precedence,function);
    }
}
