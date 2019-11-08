package com.xplusj.operator.binary;

import com.xplusj.GlobalContext;
import com.xplusj.operator.Operator;

import static java.lang.String.format;

public class BinaryOperator implements Operator<BinaryOperatorContext> {
    private final GlobalContext context;
    private final BinaryOperatorDefinition definition;

    private BinaryOperator(final GlobalContext context, final BinaryOperatorDefinition definition) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public BinaryOperatorDefinition getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(params.length != 2)
            throw new IllegalArgumentException(format(
                "Binary operator %s expects two parameters, but received %s",
                definition.getSymbol(), params.length));

        return definition.getFunction().apply(new BinaryOperatorContext(context,params));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static BinaryOperator create(final GlobalContext context, final BinaryOperatorDefinition definition) {
        return new BinaryOperator(context,definition);
    }
}
