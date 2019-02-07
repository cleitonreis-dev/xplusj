package com.xplusj.operator;

import com.xplusj.RuntimeContext;

public interface OperatorRuntimeContext extends RuntimeContext {
    double getFirstValue();
    double getSecondValue();
}
