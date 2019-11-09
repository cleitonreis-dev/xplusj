package com.xplusj.operator;

import com.xplusj.GlobalContext;

import static java.lang.String.format;

public abstract class OperatorContext{
    private final GlobalContext context;
    private final double[] params;

    protected OperatorContext(final GlobalContext context, final double...params) {
        this.context = context;
        this.params = params;
    }

    public double call(final String name, final double...values){
        if(!context.hasFunction(name))
            throw new IllegalArgumentException(format("Function '%s' not found", name));


        return context.getFunction(name).execute(values);
    }

    public double getConstant(String name){
        if(!context.hasConstant(name))
            throw new IllegalArgumentException(format("Constant '%s' not found", name));

        return context.getConstant(name);
    }

    public double param(int index){
        if(index < 0 || index >= params.length)
            throw new IllegalArgumentException(format(
                    "Invalid param index '%s'. Valid indexes are from %s to %s",
                    index, 0, params.length-1));

        return params[index];
    }
}
