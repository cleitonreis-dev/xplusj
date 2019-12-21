package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

public class DefaultExpressionUnaryOperatorFactory implements ExpressionUnaryOperatorFactory {

    protected DefaultExpressionUnaryOperatorFactory(){}

    @Override
    public UnaryOperator create(UnaryOperatorDefinition definition, ExpressionContext context) {
        return null;
    }

    public static DefaultExpressionUnaryOperatorFactory create(){
        return new DefaultExpressionUnaryOperatorFactory();
    }
}
