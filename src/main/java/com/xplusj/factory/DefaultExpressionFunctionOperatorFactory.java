package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.function.FunctionOperatorDefinition;

public class DefaultExpressionFunctionOperatorFactory implements ExpressionFunctionOperatorFactory {

    protected DefaultExpressionFunctionOperatorFactory(){}

    @Override
    public FunctionOperator create(FunctionOperatorDefinition definition, ExpressionContext context) {
        return null;
    }

    public static DefaultExpressionFunctionOperatorFactory create(){
        return new DefaultExpressionFunctionOperatorFactory();
    }
}
