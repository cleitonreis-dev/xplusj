package com.xplusj.expression;

import com.xplusj.operation.Operation;
import com.xplusj.operation.RuntimeContext;
import com.xplusj.stack.Stack;

interface StackBasedExecutor {
    Operation<? extends RuntimeContext> getOperation();
    void execute(Stack<Double> valueStack);
    boolean precedes(StackBasedExecutor executor);
}
