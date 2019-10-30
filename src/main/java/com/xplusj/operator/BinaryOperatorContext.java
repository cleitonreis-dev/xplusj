package com.xplusj.operator;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;

public class BinaryOperatorContext extends OperatorContext {

    private final double[] params;

    public BinaryOperatorContext(Environment env, OperatorContextFunctionCaller functionCaller, double...params) {
        super(env, functionCaller);

        if(params.length != 2)
            throw new IllegalArgumentException("Two parameters are required");

        this.params = params;
    }

    public BinaryOperatorContext(Environment env, double...params) {
        this(env, OperatorContextFunctionCaller.DEFAULT,params);
    }

    public double getFirstValue(){
        return params[0];
    }


    public double getSecondValue(){
        return params[1];
    }
}
