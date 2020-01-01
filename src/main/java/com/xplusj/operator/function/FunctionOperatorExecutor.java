package com.xplusj.operator.function;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.OperatorExecutor;

import static java.lang.String.format;

public class FunctionOperatorExecutor implements OperatorExecutor<FunctionOperatorContext> {
    private final ExpressionContext context;
    private final FunctionOperator definition;

    FunctionOperatorExecutor(final ExpressionContext context, final FunctionOperator definition) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public FunctionOperator getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(!definition.isVarArgs() && params.length != definition.getParamsLength())
            throw new IllegalArgumentException(format(
                    "Function '%s' expects %s parameter(s), but received %s",
                    definition.getIdentifier(), definition.getParamsLength(), params.length));

        if(definition.isVarArgs() && params.length < definition.getParamsLength() - 1)
            throw new IllegalArgumentException(format(
                    "Function '%s' expects at least %s parameter(s), but received %s",
                    definition.getIdentifier(), definition.getParamsLength()-1, params.length));

        return definition.getFunction().apply(FunctionOperatorContext.create(definition,context,params));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static FunctionOperatorExecutor create(final ExpressionContext context, final FunctionOperator definition){
        return new FunctionOperatorExecutor(context, definition);
    }
}
