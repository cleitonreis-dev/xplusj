package com.xplusj.operator.function;

import com.xplusj.GlobalContext;
import com.xplusj.operator.OperatorContext;

public class FunctionOperatorContext extends OperatorContext{

    private final FunctionOperatorDefinition function;

    private FunctionOperatorContext(final FunctionOperatorDefinition function, final GlobalContext context, final double...values) {
        super(context,values);
        this.function = function;
    }

    public double param(String name){
        return param(function.paramIndex(name));
    }

    public static FunctionOperatorContext create(FunctionOperatorDefinition function, GlobalContext context, double...values){
        return new FunctionOperatorContext(function, context, values);
    }
}
