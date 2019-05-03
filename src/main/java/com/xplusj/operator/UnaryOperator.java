package com.xplusj.operator;

import java.util.function.Function;

public interface UnaryOperator extends Operator<UnaryOperatorContext> {
    char getSymbol();

    static UnaryOperator create(char symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        return new com.xplusj.interpreter.operator.UnaryOperator(symbol,precedence,function);
    }
}
