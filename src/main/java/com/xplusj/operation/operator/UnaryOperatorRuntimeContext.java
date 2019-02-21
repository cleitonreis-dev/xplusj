package com.xplusj.operation.operator;

import com.xplusj.Environment;
import com.xplusj.operation.RuntimeContext;

public class UnaryOperatorRuntimeContext extends RuntimeContext {

    private final double value;

    public UnaryOperatorRuntimeContext(double value, Environment env) {
        super(env);
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
