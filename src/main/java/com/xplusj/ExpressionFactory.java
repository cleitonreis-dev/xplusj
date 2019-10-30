package com.xplusj;


import com.xplusj.expression.DefaultExpressionFactory;

public interface ExpressionFactory {
    Expression expression(String expression, Environment env);

    Expression formula(String expression, Environment env);

    static ExpressionFactory defaultFactory(){
        return DefaultExpressionFactory.getInstance();
    }
}
