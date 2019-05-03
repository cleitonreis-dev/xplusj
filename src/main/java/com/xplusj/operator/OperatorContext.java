package com.xplusj.operator;

public interface OperatorContext {
    double call(String name, double...params);

    double getConstant(String name);
}
