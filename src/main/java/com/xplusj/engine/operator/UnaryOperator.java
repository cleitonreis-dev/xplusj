package com.xplusj.engine.operator;

import com.xplusj.core.operator.OperatorType;
import com.xplusj.core.operator.Precedence;
import com.xplusj.core.operator.UnaryOperatorContext;

import java.util.function.Function;

public class UnaryOperator extends AbstractOperator<com.xplusj.core.operator.UnaryOperatorContext>
        implements com.xplusj.core.operator.UnaryOperator {

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
}
