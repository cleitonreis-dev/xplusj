package com.xplusj.function;

import com.xplusj.RuntimeContext;

public interface FunctionRuntimeContext extends RuntimeContext {
    double getParam(String name);
}
