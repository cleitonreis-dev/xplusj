package com.xplusj.operation;

import java.util.function.Function;

public interface Operation<T extends RuntimeContext> extends OperationVisitable {

    OperationType getOperationType();

    Precedence getOperationPrecedence();

    Function<T,Double> getFunction();

    boolean precedes(Operation<?> executor);
}
