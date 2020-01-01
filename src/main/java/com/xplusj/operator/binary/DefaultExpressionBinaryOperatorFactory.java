package com.xplusj.operator.binary;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionBinaryOperatorFactory;

public class DefaultExpressionBinaryOperatorFactory implements ExpressionBinaryOperatorFactory {
    private static final DefaultExpressionBinaryOperatorFactory INSTANCE = new DefaultExpressionBinaryOperatorFactory();

    @Override
    public BinaryOperatorExecutor create(BinaryOperator definition, ExpressionContext context) {
        return BinaryOperatorExecutor.create(context,definition);
    }

    public static DefaultExpressionBinaryOperatorFactory getInstance(){
        return INSTANCE;
    }
}
