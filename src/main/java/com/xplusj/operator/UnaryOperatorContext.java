package com.xplusj.operator;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;

public class UnaryOperatorContext extends OperatorContext{

    private final double value;

    public UnaryOperatorContext(Environment env, OperatorContextFunctionCaller functionCaller, double value) {
        super(env, functionCaller);
        this.value = value;
    }

    public UnaryOperatorContext(Environment env, double value) {
        this(env, OperatorContextFunctionCaller.DEFAULT, value);
    }

    public double getValue(){
        return value;
    }
}
