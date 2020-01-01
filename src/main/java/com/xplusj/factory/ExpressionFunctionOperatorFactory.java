package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.operator.function.DefaultExpressionFunctionOperatorFactory;
import com.xplusj.operator.function.FunctionOperatorExecutor;
import com.xplusj.operator.function.FunctionOperator;

public interface ExpressionFunctionOperatorFactory {
    FunctionOperatorExecutor create(FunctionOperator definition, ExpressionContext context);

    static ExpressionFunctionOperatorFactory defaultFactory() {
        return DefaultExpressionFunctionOperatorFactory.getInstance();
    }
}
