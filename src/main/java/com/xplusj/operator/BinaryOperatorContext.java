package com.xplusj.operator;

import com.xplusj.core.GlobalContext;

public class BinaryOperatorContext extends AbstractOperatorContext
        implements com.xplusj.core.operator.BinaryOperatorContext {

    private final double[] params;

    public BinaryOperatorContext(GlobalContext context, OperatorContextFunctionCaller functionCaller, double...params) {
        super(context, functionCaller);

        if(params.length != 2)
            throw new IllegalArgumentException("Two parameters are required");

        this.params = params;
    }

    public BinaryOperatorContext(GlobalContext context, double...params) {
        this(context,OperatorContextFunctionCaller.DEFAULT,params);
    }

    public double getFirstValue(){
        return params[0];
    }


    public double getSecondValue(){
        return params[1];
    }
}