package com.xplusj.operator.unary;

import com.xplusj.GlobalContext;
import com.xplusj.operator.OperatorContext;

public class UnaryOperatorContext extends OperatorContext {

    private final double value;

    UnaryOperatorContext(final GlobalContext context, final double value) {
        super(context);
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
