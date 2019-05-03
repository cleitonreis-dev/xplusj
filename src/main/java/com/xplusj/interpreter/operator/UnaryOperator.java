package com.xplusj.interpreter.operator;

import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;
import com.xplusj.operator.UnaryOperatorContext;

import java.util.function.Function;

public class UnaryOperator extends AbstractOperator<com.xplusj.operator.UnaryOperatorContext>
        implements com.xplusj.operator.UnaryOperator {

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
