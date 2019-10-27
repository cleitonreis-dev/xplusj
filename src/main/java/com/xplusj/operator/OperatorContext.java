package com.xplusj.operator;

import com.xplusj.GlobalContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class OperatorContext{
    private final GlobalContext context;
    private final OperatorContextFunctionCaller functionCaller;

    public double call(String name, double...values){
        return functionCaller.call(context,name,values);
    }

    public double getConstant(String name){
        return context.getConstant(name);
    }
}
