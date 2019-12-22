package com.xplusj.factory;


import com.xplusj.Expression;
import com.xplusj.ExpressionContext;
import com.xplusj.expression.DefaultExpressionFactory;

public interface ExpressionFactory {
    Expression expression(String expression, ExpressionContext globalContext);

    Expression formula(String expression, ExpressionContext globalContext);

    static ExpressionFactory defaultFactory(){
        return DefaultExpressionFactory.getInstance();
    }
}
