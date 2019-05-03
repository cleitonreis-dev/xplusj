package com.xplusj.core;

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
}
