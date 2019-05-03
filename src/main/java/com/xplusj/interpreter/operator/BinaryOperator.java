package com.xplusj.interpreter.operator;

import com.xplusj.operator.BinaryOperatorContext;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;

import java.util.function.Function;

public class BinaryOperator extends AbstractOperator<com.xplusj.operator.BinaryOperatorContext>
        implements com.xplusj.operator.BinaryOperator {

    public BinaryOperator(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        super(OperatorType.BINARY, symbol, precedence, function);
    }

    @Override
    public int getParamsLength() {
        return 2;
    }

    @Override
    public double execute(com.xplusj.operator.BinaryOperatorContext context) {
        return function.apply(context);
    }
}
