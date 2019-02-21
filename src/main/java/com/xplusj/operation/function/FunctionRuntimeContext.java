package com.xplusj.operation.function;

import com.xplusj.Environment;
import com.xplusj.operation.RuntimeContext;

import java.util.Map;

public class FunctionRuntimeContext extends RuntimeContext {
    private final Map<String,Double> paramValues;

    public FunctionRuntimeContext(Map<String, Double> paramValues, Environment env) {
        super(env);
        this.paramValues = paramValues;
    }

    public double param(String name){
        return paramValues.get(name);
    }
}
