package com.xplusj;

public interface RuntimeContext{
    FunctionCaller getFunction(String name);

    Double getConstant(String name);
}
