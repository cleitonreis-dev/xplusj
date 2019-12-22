package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.function.DefaultExpressionFunctionOperatorFactory;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.function.FunctionOperatorDefinition;

public interface ExpressionFunctionOperatorFactory {
    FunctionOperator create(FunctionOperatorDefinition definition, ExpressionContext context);

    static ExpressionFunctionOperatorFactory defaultFactory() {
        return DefaultExpressionFunctionOperatorFactory.getInstance();
    }
}
