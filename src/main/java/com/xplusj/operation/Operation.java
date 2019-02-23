package com.xplusj.operation;

import java.util.function.Function;

public interface Operation<T extends RuntimeContext> extends OperationVisitable {

    OperationType geType();

    Precedence getPrecedence();

    Function<T,Double> getFunction();

    boolean precedes(Operation<?> executor);
}
