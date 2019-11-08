package com.xplusj.operator.binary;

import com.xplusj.GlobalContext;
import com.xplusj.operator.OperatorContext;

public class BinaryOperatorContext extends OperatorContext {

    private final double[] params;

    BinaryOperatorContext(final GlobalContext context, final double...params) {
        super(context);
        this.params = params;
    }

    public double getFirstValue(){
        return params[0];
    }


    public double getSecondValue(){
        return params[1];
    }
}
