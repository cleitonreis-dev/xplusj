package com.xplusj.operator;

public interface Operator<T extends OperatorContext> {
    OperatorDefinition<T> getDefinition();

    double execute(double...params);
}
