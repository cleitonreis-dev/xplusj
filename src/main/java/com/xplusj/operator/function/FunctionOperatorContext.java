package com.xplusj.operator.function;

import com.xplusj.GlobalContext;
import com.xplusj.operator.OperatorContext;

;

public class FunctionOperatorContext extends OperatorContext{

    private final double[] values;
    private final FunctionOperatorDefinition function;

    private FunctionOperatorContext(final FunctionOperatorDefinition function, final GlobalContext context, final double...values) {
        super(context);
        this.values = values;
        this.function = function;
    }

    public double param(String name){
        return values[function.paramIndex(name)];
    }

    public double param(int index){
        return values[index];
    }

    public static FunctionOperatorContext create(FunctionOperatorDefinition function, GlobalContext context, double...values){
        return new FunctionOperatorContext(function, context, values);
    }
}
