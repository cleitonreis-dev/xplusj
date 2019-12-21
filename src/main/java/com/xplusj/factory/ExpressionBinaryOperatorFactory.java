package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorDefinition;

public interface ExpressionBinaryOperatorFactory {
    BinaryOperator create(BinaryOperatorDefinition definition, ExpressionContext context);

    static ExpressionBinaryOperatorFactory defaultFactory() {
        return DefaultExpressionBinaryOperatorFactory.create();
    }
}
