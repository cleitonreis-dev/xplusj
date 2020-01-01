package com.xplusj.operator.function;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.OperatorContext;

public class FunctionOperatorContext extends OperatorContext{

    private final FunctionOperator function;

    private FunctionOperatorContext(final FunctionOperator function, final ExpressionContext context, final double...values) {
        super(context,values);
        this.function = function;
    }

    public double param(String name){
        return param(function.paramIndex(name));
    }

    public static FunctionOperatorContext create(FunctionOperator function, ExpressionContext context, double...values){
        return new FunctionOperatorContext(function, context, values);
    }

    @Override
    protected ExpressionContext getContext() {
        return super.getContext();
    }
}
