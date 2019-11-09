package com.xplusj.operator.unary;

import com.xplusj.GlobalContext;
import com.xplusj.operator.OperatorContext;

public class UnaryOperatorContext extends OperatorContext {

    UnaryOperatorContext(final GlobalContext context, final double value) {
        super(context,value);
    }

    public double param(){
        return param(0);
    }
}
