package com.xplusj.operation;

import com.xplusj.Environment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class RuntimeContext{
    private final Environment env;

    public FunctionCaller getFunction(String name){
        return new RuntimeFunctionCaller(env.getFunction(name), env);
    }

    public Double getConstant(String name){
        return env.getConstant(name);
    }
}
