package com.xplusj.operator;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;

public class FunctionOperatorContext extends OperatorContext{

    private final double[] values;
    private final FunctionOperator function;

    public FunctionOperatorContext(FunctionOperator function, Environment env, OperatorContextFunctionCaller caller, double...values) {
        super(env, caller);
        this.values = values;
        this.function = function;
    }

    public FunctionOperatorContext(FunctionOperator function, Environment env, double...values) {
        this(function,env, OperatorContextFunctionCaller.DEFAULT,values);
    }

    public double param(String name){
        return values[function.paramIndex(name)];
    }

    public double param(int index){
        return values[index];
    }
}
