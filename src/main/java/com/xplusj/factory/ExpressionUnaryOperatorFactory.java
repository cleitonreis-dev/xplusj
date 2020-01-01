package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.unary.DefaultExpressionUnaryOperatorFactory;
import com.xplusj.operator.unary.UnaryOperatorExecutor;
import com.xplusj.operator.unary.UnaryOperator;

public interface ExpressionUnaryOperatorFactory {
    UnaryOperatorExecutor create(UnaryOperator definition, ExpressionContext context);

    static ExpressionUnaryOperatorFactory defaultFactory(){
        return DefaultExpressionUnaryOperatorFactory.getInstance();
    }
}
