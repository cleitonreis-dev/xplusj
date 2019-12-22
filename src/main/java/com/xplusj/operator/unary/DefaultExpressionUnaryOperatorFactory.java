package com.xplusj.operator.unary;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionUnaryOperatorFactory;

public class DefaultExpressionUnaryOperatorFactory implements ExpressionUnaryOperatorFactory {
    private static final DefaultExpressionUnaryOperatorFactory INSTANCE = new DefaultExpressionUnaryOperatorFactory();

    @Override
    public UnaryOperator create(UnaryOperatorDefinition definition, ExpressionContext context) {
        return UnaryOperator.create(definition, context);
    }

    public static DefaultExpressionUnaryOperatorFactory getInstance(){
        return INSTANCE;
    }
}
