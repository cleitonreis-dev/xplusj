package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.unary.DefaultExpressionUnaryOperatorFactory;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

public interface ExpressionUnaryOperatorFactory {
    UnaryOperator create(UnaryOperatorDefinition definition, ExpressionContext context);

    static ExpressionUnaryOperatorFactory defaultFactory(){
        return DefaultExpressionUnaryOperatorFactory.getInstance();
    }
}
