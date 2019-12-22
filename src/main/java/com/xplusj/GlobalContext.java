package com.xplusj;

import com.xplusj.context.DefaultGlobalContext;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

//TODO delete
public interface GlobalContext {

    boolean hasFunction(String name);

    boolean hasBinaryOperator(char symbol);

    boolean hasUnaryOperator(char symbol);

    boolean hasConstant(String name);

    BinaryOperator getBinaryOperator(char symbol);

    UnaryOperator getUnaryOperator(char symbol);

    FunctionOperator getFunction(String name);

    double getConstant(String name);

    interface Builder{
        Builder addUnaryOperator(UnaryOperatorDefinition...operator);

        Builder addBinaryOperator(BinaryOperatorDefinition...operator);

        Builder addFunction(FunctionOperatorDefinition...function);

        Builder addConstant(String name, double value);

        GlobalContext build();
    }

    static GlobalContext.Builder builder(){
        return DefaultGlobalContext.builder();
    }
}
