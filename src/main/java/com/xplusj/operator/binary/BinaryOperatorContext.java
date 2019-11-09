package com.xplusj.operator.binary;

import com.xplusj.GlobalContext;
import com.xplusj.operator.OperatorContext;

public class BinaryOperatorContext extends OperatorContext {

    BinaryOperatorContext(final GlobalContext context, final double...params) {
        super(context,params);
    }

    public double param0(){
        return param(0);
    }

    public double param1(){
        return param(1);
    }
}
