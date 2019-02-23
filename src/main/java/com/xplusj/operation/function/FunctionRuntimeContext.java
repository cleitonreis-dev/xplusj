package com.xplusj.operation.function;

import com.xplusj.Environment;
import com.xplusj.operation.RuntimeContext;

import java.util.Map;

public class FunctionRuntimeContext extends RuntimeContext {
    private final double[] values;
    private final Map<String,Integer> indexes;

    public FunctionRuntimeContext(double[] values, Map<String,Integer> indexes, Environment env) {
        super(env);
        this.values = values;
        this.indexes = indexes;
    }

    public double param(String name){
        return values[indexes.get(name)];
    }
}
