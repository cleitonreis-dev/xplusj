package com.xplusj;

import com.xplusj.context.DefaultExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.OperatorDefinition;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import java.util.Set;

public interface ExpressionOperatorDefinitions {
    enum ListOperatorFilter {ALL,UNARY,BINARY,UNARY_AND_BINARY,FUNCTION}

    boolean hasFunction(String name);

    boolean hasBinaryOperator(String symbol);

    boolean hasUnaryOperator(String symbol);

    boolean hasConstant(String name);

    BinaryOperatorDefinition getBinaryOperator(String symbol);

    UnaryOperatorDefinition getUnaryOperator(String symbol);

    Set<OperatorDefinition<?>> list(ListOperatorFilter listOperatorFilter);

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
