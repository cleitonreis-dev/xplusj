package com.xplusj;


import com.xplusj.expression.DefaultExpressionFactory;

//TODO delete
public interface ExpressionFactory {
    Expression expression(String expression, Environment env);

    Expression formula(String expression, Environment env);

    static ExpressionFactory defaultFactory(){
        return null;
    }
}
