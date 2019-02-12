package com.xplusj;

public interface OperationExecutor<T extends RuntimeContext> {

    OperationType getOperationType();

    OperationPrecedence getOperationPrecedence();

    boolean precedes(OperationExecutor<?> executor);

    double execute(T context);
}
