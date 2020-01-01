package com.xplusj;

import com.xplusj.context.DefaultExpressionOperators;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;

public interface ExpressionOperators {

    boolean hasFunction(String name);

    boolean hasBinaryOperator(String symbol);

    boolean hasUnaryOperator(String symbol);

    boolean hasConstant(String name);

    BinaryOperator getBinaryOperator(String symbol);

    UnaryOperator getUnaryOperator(String symbol);

    FunctionOperator getFunction(String name);

    Variable getConstant(String name);

    ExpressionOperators append(ExpressionOperators newDefinitions);

    interface Builder{
        ExpressionOperators.Builder addUnaryOperator(UnaryOperator...operator);

        ExpressionOperators.Builder addBinaryOperator(BinaryOperator...operator);

        ExpressionOperators.Builder addFunction(FunctionOperator...function);

        ExpressionOperators.Builder addConstant(Variable...constants);

        ExpressionOperators build();
    }

    static ExpressionOperators.Builder builder(){
        return DefaultExpressionOperators.builder();
    }
}
