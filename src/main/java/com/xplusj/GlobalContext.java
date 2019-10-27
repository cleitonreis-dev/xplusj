package com.xplusj;

import com.xplusj.context.DefaultGlobalContext;
import com.xplusj.operator.*;

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
        Builder addUnaryOperator(UnaryOperator...operator);

        Builder addBinaryOperator(BinaryOperator...operator);

        Builder addFunction(FunctionOperator...function);

        Builder addConstant(String name, double value);

        GlobalContext build();
    }

    static GlobalContext.Builder builder(){
        return DefaultGlobalContext.builder();
    }
}
