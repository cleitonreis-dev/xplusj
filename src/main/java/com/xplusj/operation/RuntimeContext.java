package com.xplusj.operation;

public interface RuntimeContext{
    FunctionCaller getFunction(String name);

    Double getConstant(String name);
}
