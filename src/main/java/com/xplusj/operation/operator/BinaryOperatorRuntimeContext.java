package com.xplusj.operation.operator;

import com.xplusj.Environment;
import com.xplusj.operation.RuntimeContext;

public class BinaryOperatorRuntimeContext extends RuntimeContext {
    private final double firstValue;
    private final double secondValue;

    public BinaryOperatorRuntimeContext(double firstValue, double secondValue, Environment env) {
        super(env);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public double getFirstValue(){
        return firstValue;
    }


    public double getSecondValue(){
        return secondValue;
    }
}
