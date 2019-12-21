package com.xplusj;

import com.xplusj.context.DefaultExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

public interface ExpressionOperatorDefinitions {
    boolean hasFunction(String name);

    boolean hasBinaryOperator(char symbol);

    boolean hasUnaryOperator(char symbol);

    boolean hasConstant(String name);

    BinaryOperatorDefinition getBinaryOperator(char symbol);

    UnaryOperatorDefinition getUnaryOperator(char symbol);

    FunctionOperatorDefinition getFunction(String name);

    Constant getConstant(String name);

    ExpressionOperatorDefinitions append(ExpressionOperatorDefinitions newDefinitions);

    interface Builder{
        ExpressionOperatorDefinitions.Builder addUnaryOperator(UnaryOperatorDefinition...operator);

        ExpressionOperatorDefinitions.Builder addBinaryOperator(BinaryOperatorDefinition...operator);

        ExpressionOperatorDefinitions.Builder addFunction(FunctionOperatorDefinition...function);

        ExpressionOperatorDefinitions.Builder addConstant(Constant...constants);

        ExpressionOperatorDefinitions build();
    }

    static ExpressionOperatorDefinitions.Builder builder(){
        return DefaultExpressionOperatorDefinitions.builder();
    }
}
