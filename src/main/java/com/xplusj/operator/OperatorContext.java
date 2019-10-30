package com.xplusj.operator;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;

public abstract class OperatorContext{
    private final GlobalContext context;
    private final OperatorContextFunctionCaller functionCaller;
    private final Environment environment;

    public OperatorContext(Environment environment, OperatorContextFunctionCaller functionCaller) {
        this.context = environment.getContext();
        this.functionCaller = functionCaller;
        this.environment = environment;
    }

    public double call(String name, double...values){
        return functionCaller.call(this.environment,name,values);
    }

    public double getConstant(String name){
        return context.getConstant(name);
    }

    protected Environment getEnvironment() {
        return environment;
    }
}
