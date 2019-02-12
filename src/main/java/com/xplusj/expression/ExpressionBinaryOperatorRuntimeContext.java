package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.operator.BinaryOperatorRuntimeContext;

public class ExpressionBinaryOperatorRuntimeContext
        extends ExpressionRutimeContext
        implements BinaryOperatorRuntimeContext {

    private final double firstValue;
    private final double secondValue;

    ExpressionBinaryOperatorRuntimeContext(double secondValue, double firstValue, Environment env) {
        super(env);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    @Override
    public double getFirstValue() {
        return firstValue;
    }

    @Override
    public double getSecondValue() {
        return secondValue;
    }
}
