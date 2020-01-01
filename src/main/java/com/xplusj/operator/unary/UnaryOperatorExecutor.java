package com.xplusj.operator.unary;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.OperatorExecutor;

import static java.lang.String.format;

public class UnaryOperatorExecutor implements OperatorExecutor<UnaryOperatorContext> {
    private final ExpressionContext context;
    private final UnaryOperator definition;

    private UnaryOperatorExecutor(final UnaryOperator definition, final ExpressionContext context) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public UnaryOperator getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(params.length != 1)
            throw new IllegalArgumentException(format(
                "Unary operator %s expects one parameter, but received %s",
                definition.getIdentifier(), params.length));

        return definition.getFunction().apply(new UnaryOperatorContext(context,params[0]));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static UnaryOperatorExecutor create(final UnaryOperator definition, final ExpressionContext context) {
        return new UnaryOperatorExecutor(definition, context);
    }
}
