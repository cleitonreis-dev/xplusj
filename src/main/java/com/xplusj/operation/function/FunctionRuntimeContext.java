package com.xplusj.operation.function;

import com.xplusj.operation.RuntimeContext;

public interface FunctionRuntimeContext extends RuntimeContext {
    double getParam(String name);
}
