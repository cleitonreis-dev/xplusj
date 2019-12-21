package com.xplusj.factory;


import com.xplusj.Expression;
import com.xplusj.ExpressionContext;

public interface ExpressionFactory {
    Expression expression(String expression, ExpressionContext globalContext);

    Expression formula(String expression, ExpressionContext globalContext);

    static ExpressionFactory defaultFactory(){
        return DefaultExpressionFactory.create();
    }
}
