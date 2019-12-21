package com.xplusj.factory;

import com.xplusj.Expression;
import com.xplusj.ExpressionContext;

public class DefaultExpressionFactory implements ExpressionFactory {

    protected DefaultExpressionFactory(){}

    @Override
    public Expression expression(String expression, ExpressionContext globalContext) {
        return null;
    }

    @Override
    public Expression formula(String expression, ExpressionContext globalContext) {
        return null;
    }

    public static DefaultExpressionFactory create(){
        return new DefaultExpressionFactory();
    }
}
