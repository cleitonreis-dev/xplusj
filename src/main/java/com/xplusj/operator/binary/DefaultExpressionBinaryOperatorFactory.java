package com.xplusj.operator.binary;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionBinaryOperatorFactory;

public class DefaultExpressionBinaryOperatorFactory implements ExpressionBinaryOperatorFactory {
    private static final DefaultExpressionBinaryOperatorFactory INSTANCE = new DefaultExpressionBinaryOperatorFactory();

    @Override
    public BinaryOperator create(BinaryOperatorDefinition definition, ExpressionContext context) {
        return BinaryOperator.create(context,definition);
    }

    public static DefaultExpressionBinaryOperatorFactory getInstance(){
        return INSTANCE;
    }
}
