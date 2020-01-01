package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.binary.BinaryOperatorExecutor;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.DefaultExpressionBinaryOperatorFactory;

public interface ExpressionBinaryOperatorFactory {
    BinaryOperatorExecutor create(BinaryOperator definition, ExpressionContext context);

    static ExpressionBinaryOperatorFactory defaultFactory() {
        return DefaultExpressionBinaryOperatorFactory.getInstance();
    }
}
