package com.xplusj.operator;

import com.xplusj.GlobalContext;

import static java.lang.String.format;

public abstract class OperatorContext{
    private final GlobalContext context;

    protected OperatorContext(GlobalContext context) {
        this.context = context;
    }

    public double call(String name, double...values){
        if(!context.hasFunction(name))
            throw new IllegalArgumentException(format("Function '%s' not found", name));


        return context.getFunction(name).execute(values);
    }

    public double getConstant(String name){
        if(!context.hasConstant(name))
            throw new IllegalArgumentException(format("Constant '%s' not found", name));

        return context.getConstant(name);
    }
}
