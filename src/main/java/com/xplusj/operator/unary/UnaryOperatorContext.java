package com.xplusj.operator.unary;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.OperatorContext;

public class UnaryOperatorContext extends OperatorContext {

    UnaryOperatorContext(final ExpressionContext context, final double value) {
        super(context,value);
    }

    public double param(){
        return param(0);
    }
}
