package com.xplusj.operator;

import java.util.function.Function;

public interface BinaryOperator extends Operator<BinaryOperatorContext> {
    char getSymbol();

    static BinaryOperator create(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        return new com.xplusj.interpreter.operator.BinaryOperator(symbol,precedence,function);
    }
}
