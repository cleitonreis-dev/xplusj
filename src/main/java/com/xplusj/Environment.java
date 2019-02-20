package com.xplusj;

import com.xplusj.function.ExpressionFunction;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.UnaryOperatorRuntimeContext;

public interface Environment {
    boolean hasFunction(String name);

    boolean hasBinaryOperator(char symbol);

    boolean hasUnaryOperator(char symbol);

    boolean hasConstant(String name);

    Operator<BinaryOperatorRuntimeContext> getBinaryOperator(char symbol);

    Operator<UnaryOperatorRuntimeContext> getUnaryOperator(char symbol);

    ExpressionFunction getFunction(String name);

    @Deprecated//TODO Expression factory will create expressions
    ExpressionEvaluator expression(String expression);

    Double getConstant(String name);

    static DefaultEnvironment.Builder defaultEnv(){
        return DefaultEnvironment.builder();
    }
}
