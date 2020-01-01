package com.xplusj.operator;

public interface OperatorExecutor<T extends OperatorContext> {
    Operator<T> getDefinition();

    double execute(double...params);
}
