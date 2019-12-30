package com.xplusj.operator.function;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.Operator;

import static java.lang.String.format;

public class FunctionOperator implements Operator<FunctionOperatorContext> {
    private final ExpressionContext context;
    private final FunctionOperatorDefinition definition;

    FunctionOperator(final ExpressionContext context, final FunctionOperatorDefinition definition) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public FunctionOperatorDefinition getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(!definition.isVarArgs() && params.length != definition.getParamsLength())
            throw new IllegalArgumentException(format(
                    "Function '%s' expects %s parameter(s), but received %s",
                    definition.getName(), definition.getParamsLength(), params.length));

        if(definition.isVarArgs() && params.length < definition.getParamsLength() - 1)
            throw new IllegalArgumentException(format(
                    "Function '%s' expects at least %s parameter(s), but received %s",
                    definition.getName(), definition.getParamsLength()-1, params.length));

        return definition.getFunction().apply(FunctionOperatorContext.create(definition,context,params));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static FunctionOperator create(final ExpressionContext context, final FunctionOperatorDefinition definition){
        return new FunctionOperator(context, definition);
    }
}
