package com.xplusj.operation.operator;

import com.xplusj.operation.RuntimeContext;

public interface BinaryOperatorRuntimeContext extends RuntimeContext {
    double getFirstValue();
    double getSecondValue();
}
