package com.xplusj.operation;

public interface Operation<T extends RuntimeContext> {

    OperationType getOperationType();

    Precedence getOperationPrecedence();

    boolean precedes(Operation<?> executor);

    double execute(T context);
}
