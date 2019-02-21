package com.xplusj;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operation.operator.Operator;
import com.xplusj.operation.operator.UnaryOperatorRuntimeContext;

public interface Environment {
    boolean hasFunction(String name);

    boolean hasBinaryOperator(char symbol);

    boolean hasUnaryOperator(char symbol);

    boolean hasConstant(String name);

    Operator<BinaryOperatorRuntimeContext> getBinaryOperator(char symbol);

    Operator<UnaryOperatorRuntimeContext> getUnaryOperator(char symbol);

    ExpressionFunction getFunction(String name);

    Double getConstant(String name);

    static DefaultEnvironment.Builder defaultEnv(){
        return DefaultEnvironment.builder();
    }
}
