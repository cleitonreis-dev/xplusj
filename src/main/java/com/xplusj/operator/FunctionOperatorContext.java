package com.xplusj.operator;

import com.xplusj.GlobalContext;

public class FunctionOperatorContext extends OperatorContext{

    private final double[] values;
    private final FunctionOperator function;

    public FunctionOperatorContext(FunctionOperator function, GlobalContext context, OperatorContextFunctionCaller caller, double...values) {
        super(context, caller);
        this.values = values;
        this.function = function;
    }

    public FunctionOperatorContext(FunctionOperator function, GlobalContext context, double...values) {
        this(function,context, OperatorContextFunctionCaller.DEFAULT,values);
    }

    public double param(String name){
        return values[function.paramIndex(name)];
    }

    public double param(int index){
        return values[index];
    }
}
