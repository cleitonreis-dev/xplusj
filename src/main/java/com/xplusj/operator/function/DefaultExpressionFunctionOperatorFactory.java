package com.xplusj.operator.function;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionFunctionOperatorFactory;

public class DefaultExpressionFunctionOperatorFactory implements ExpressionFunctionOperatorFactory {
    private static final DefaultExpressionFunctionOperatorFactory INSTANCE = new DefaultExpressionFunctionOperatorFactory();

    @Override
    public FunctionOperator create(FunctionOperatorDefinition definition, ExpressionContext context) {
        return FunctionOperator.create(context,definition);
    }

    public static DefaultExpressionFunctionOperatorFactory getInstance(){
        return INSTANCE;
    }
}
