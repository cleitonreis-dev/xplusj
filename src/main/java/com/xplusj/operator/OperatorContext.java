package com.xplusj.operator;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperatorDefinitions;

import java.util.Arrays;

import static java.lang.String.format;

public abstract class OperatorContext{
    private final ExpressionContext context;
    private final ExpressionOperatorDefinitions definitions;
    private final double[] params;

    protected OperatorContext(final ExpressionContext context, final double...params) {
        this.context = context;
        this.definitions = context.getDefinitions();
        this.params = params;
    }

    public double call(final String name, final double...values){
        if(!definitions.hasFunction(name))
            throw new IllegalArgumentException(format("Function '%s' not found", name));


        return context.getFunction(name).execute(values);
    }

    public double getConstant(String name){
        if(!definitions.hasConstant(name))
            throw new IllegalArgumentException(format("Constant '%s' not found", name));

        return definitions.getConstant(name).getValue();
    }

    public double param(int index){
        if(index < 0 || index >= params.length)
            throw new IllegalArgumentException(format(
                    "Invalid param index '%s'. Valid indexes are from %s to %s",
                    index, 0, params.length-1));

        return params[index];
    }

    public double[] params(){
        return params;
    }

    protected ExpressionContext getContext(){
        return context;
    }
}
