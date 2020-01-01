package com.xplusj.operator.unary;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionUnaryOperatorFactory;

public class DefaultExpressionUnaryOperatorFactory implements ExpressionUnaryOperatorFactory {
    private static final DefaultExpressionUnaryOperatorFactory INSTANCE = new DefaultExpressionUnaryOperatorFactory();

    @Override
    public UnaryOperatorExecutor create(UnaryOperator definition, ExpressionContext context) {
        return UnaryOperatorExecutor.create(definition, context);
    }

    public static DefaultExpressionUnaryOperatorFactory getInstance(){
        return INSTANCE;
    }
}
