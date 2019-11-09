package com.xplusj.operator.function;

import com.xplusj.GlobalContext;
import com.xplusj.operator.Operator;

import static java.lang.String.format;

public class FunctionOperator implements Operator<FunctionOperatorContext> {
    private final GlobalContext context;
    private final FunctionOperatorDefinition definition;

    FunctionOperator(final GlobalContext context, final FunctionOperatorDefinition definition) {
        this.context = context;
        this.definition = definition;
    }

    @Override
    public FunctionOperatorDefinition getDefinition() {
        return definition;
    }

    @Override
    public double execute(double... params) {
        if(params.length != definition.getParamsLength())
            throw new IllegalArgumentException(format(
                    "Function '%s' expects %s parameter(s), but received %s",
                    definition.getName(), definition.getParamsLength(), params.length));

        return definition.getFunction().apply(FunctionOperatorContext.create(definition,context,params));
    }

    @Override
    public String toString() {
        return definition.toString();
    }

    public static FunctionOperator create(final GlobalContext context, final FunctionOperatorDefinition definition){
        return new FunctionOperator(context, definition);
    }
}
