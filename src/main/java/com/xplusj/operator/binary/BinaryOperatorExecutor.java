package com.xplusj.operator.binary;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.OperatorExecutor;

import static java.lang.String.format;

public class BinaryOperatorExecutor implements OperatorExecutor<BinaryOperatorContext> {
    private final ExpressionContext context;
    private final BinaryOperator definition;

    private BinaryOperatorExecutor(final ExpressionContext context, final BinaryOperator definition) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public BinaryOperator getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(params.length != 2)
            throw new IllegalArgumentException(format(
                "Binary operator %s expects two parameters, but received %s",
                definition.getIdentifier(), params.length));

        return definition.getFunction().apply(new BinaryOperatorContext(context,params));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static BinaryOperatorExecutor create(final ExpressionContext context, final BinaryOperator definition) {
        return new BinaryOperatorExecutor(context,definition);
    }
}
