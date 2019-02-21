package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.operation.operator.UnaryOperatorRuntimeContext;

public class ExpressionUnaryOperatorRuntimeContext
        extends ExpressionRuntimeContext
        implements UnaryOperatorRuntimeContext {

    private final double value;

    public ExpressionUnaryOperatorRuntimeContext(double value, Environment env) {
        super(env);
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }
}
