package com.xplusj;

import com.xplusj.function.ExpressionFunction;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.UnaryOperatorRuntimeContext;

public interface Environment {
    boolean hasFunction(String name);
    boolean hasOperator(char name);

    Operator<BinaryOperatorRuntimeContext> getBinaryOperator(char charAt);
    Operator<UnaryOperatorRuntimeContext> getUnaryOperator(char charAt);

    ExpressionFunction getFunction(String name);
}
