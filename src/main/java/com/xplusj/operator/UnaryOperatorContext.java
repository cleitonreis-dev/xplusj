package com.xplusj.operator;

import com.xplusj.core.GlobalContext;

public class UnaryOperatorContext extends AbstractOperatorContext
        implements com.xplusj.core.operator.UnaryOperatorContext {

    private final double value;

    public UnaryOperatorContext(GlobalContext context, OperatorContextFunctionCaller functionCaller, double value) {
        super(context, functionCaller);
        this.value = value;
    }

    public UnaryOperatorContext(GlobalContext context, double value) {
        this(context, OperatorContextFunctionCaller.DEFAULT, value);
    }

    public double getValue(){
        return value;
    }
}
