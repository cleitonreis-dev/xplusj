package com.xplusj.core.operator;

public interface FunctionOperator extends Operator<FunctionOperatorContext> {
    String getName();

    int paramIndex(String name);
}
