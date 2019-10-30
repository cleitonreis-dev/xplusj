package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.ExpressionFactory;

public class DefaultExpressionFactory implements ExpressionFactory {
    private static final DefaultExpressionFactory INSTANCE = new DefaultExpressionFactory();

    @Override
    public Expression expression(String expression, Environment env) {
        return DefaultExpression.create(expression,env);
    }

    @Override
    public Expression formula(String expression, Environment env) {
        return FormulaExpression.create(expression, env);
    }

    public static DefaultExpressionFactory getInstance(){
        return INSTANCE;
    }
}
