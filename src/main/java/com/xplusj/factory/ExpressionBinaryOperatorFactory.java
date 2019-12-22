package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.binary.DefaultExpressionBinaryOperatorFactory;

public interface ExpressionBinaryOperatorFactory {
    BinaryOperator create(BinaryOperatorDefinition definition, ExpressionContext context);

    static ExpressionBinaryOperatorFactory defaultFactory() {
        return DefaultExpressionBinaryOperatorFactory.getInstance();
    }
}
