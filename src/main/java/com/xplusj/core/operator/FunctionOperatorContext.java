package com.xplusj.core.operator;

public interface FunctionOperatorContext extends OperatorContext{
    double param(String name);

    double param(int index);
}
