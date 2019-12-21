package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorDefinition;

public class DefaultExpressionBinaryOperatorFactory implements ExpressionBinaryOperatorFactory {

    protected DefaultExpressionBinaryOperatorFactory(){}

    @Override
    public BinaryOperator create(BinaryOperatorDefinition definition, ExpressionContext context) {
        return null;
    }

    public static DefaultExpressionBinaryOperatorFactory create(){
        return new DefaultExpressionBinaryOperatorFactory();
    }
}
