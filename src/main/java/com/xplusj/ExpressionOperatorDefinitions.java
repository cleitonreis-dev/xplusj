package com.xplusj;

import com.xplusj.context.DefaultExpressionOperatorDefinitions;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.variable.Variable;

public interface ExpressionOperatorDefinitions {

    boolean hasFunction(String name);

    boolean hasBinaryOperator(String symbol);

    boolean hasUnaryOperator(String symbol);

    boolean hasConstant(String name);

    BinaryOperator getBinaryOperator(String symbol);

    UnaryOperator getUnaryOperator(String symbol);

    FunctionOperator getFunction(String name);

    Variable getConstant(String name);

    ExpressionOperatorDefinitions append(ExpressionOperatorDefinitions newDefinitions);

    interface Builder{
        ExpressionOperatorDefinitions.Builder addUnaryOperator(UnaryOperator...operator);

        ExpressionOperatorDefinitions.Builder addBinaryOperator(BinaryOperator...operator);

        ExpressionOperatorDefinitions.Builder addFunction(FunctionOperator...function);

        ExpressionOperatorDefinitions.Builder addConstant(Variable...constants);

        ExpressionOperatorDefinitions build();
    }

    static ExpressionOperatorDefinitions.Builder builder(){
        return DefaultExpressionOperatorDefinitions.builder();
    }
}
