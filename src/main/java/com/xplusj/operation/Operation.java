package com.xplusj.operation;

public interface Operation<T extends RuntimeContext> extends OperationVisitable {

    OperationType geType();

    Precedence getPrecedence();

    double execute(T context);

    boolean precedes(Operation<?> executor);
}
