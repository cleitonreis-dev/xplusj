package com.xplusj.operator.unary;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.Operator;

import static java.lang.String.format;

public class UnaryOperator implements Operator<UnaryOperatorContext> {
    private final ExpressionContext context;
    private final UnaryOperatorDefinition definition;

    private UnaryOperator(final UnaryOperatorDefinition definition, final ExpressionContext context) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public UnaryOperatorDefinition getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(params.length != 1)
            throw new IllegalArgumentException(format(
                "Unary operator %s expects one parameter, but received %s",
                definition.getSymbol(), params.length));

        return definition.getFunction().apply(new UnaryOperatorContext(context,params[0]));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static UnaryOperator create(final UnaryOperatorDefinition definition, final ExpressionContext context) {
        return new UnaryOperator(definition, context);
    }
}
