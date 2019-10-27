package com.xplusj.operator;

import java.util.function.Function;

public class BinaryOperator extends UBOperator<BinaryOperatorContext>{

    public BinaryOperator(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        super(OperatorType.BINARY, symbol, precedence, function);
    }

    @Override
    public int getParamsLength() {
        return 2;
    }

    @Override
    public double execute(BinaryOperatorContext context) {
        return function.apply(context);
    }

    public static BinaryOperator create(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        return new BinaryOperator(symbol,precedence,function);
    }
}
