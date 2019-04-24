package com.xplusj.operation.function;

import com.xplusj.Environment;
import com.xplusj.operation.RuntimeContext;

import java.util.Map;

public class FunctionRuntimeContext extends RuntimeContext {
    private final double[] values;
    private final Map<String,Integer> indexes;

    public FunctionRuntimeContext(double[] values, ExpressionFunction function, Environment env) {
        super(env);
        this.values = values;
        this.indexes = function.getParams();
    }

    public double param(String name){
        return values[indexes.get(name)];
    }

    public double param(int index){
        return values[index];
    }
}
