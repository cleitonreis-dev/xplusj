package com.xplusj.engine.operator;

import com.xplusj.core.operator.BinaryOperatorContext;
import com.xplusj.core.operator.OperatorType;
import com.xplusj.core.operator.Precedence;

import java.util.function.Function;

public class BinaryOperator extends AbstractOperator<com.xplusj.core.operator.BinaryOperatorContext>
        implements com.xplusj.core.operator.BinaryOperator {

    public BinaryOperator(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        super(OperatorType.BINARY, symbol, precedence, function);
    }

    @Override
    public int getParamsLength() {
        return 2;
    }

    @Override
    public double execute(com.xplusj.core.operator.BinaryOperatorContext context) {
        return function.apply(context);
    }
}
