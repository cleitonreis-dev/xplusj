package com.xplusj.operator;

import com.xplusj.RuntimeContext;

public interface BinaryOperatorRuntimeContext extends RuntimeContext {
    double getFirstValue();
    double getSecondValue();
}
