package com.xplusj.operator;

public interface FunctionOperatorContext extends OperatorContext{
    double param(String name);

    double param(int index);
}
