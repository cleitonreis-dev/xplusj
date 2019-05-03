package com.xplusj.interpreter.operator;

import com.xplusj.core.GlobalContext;
import com.xplusj.operator.OperatorContext;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public abstract class AbstractOperatorContext implements OperatorContext {
    private final GlobalContext context;
    private final OperatorContextFunctionCaller functionCaller;


    public double call(String name, double...values){
        return functionCaller.call(context,name,values);
    }

    public double getConstant(String name){
        return context.getConstant(name);
    }
}
