package com.xplusj.expression;

import com.xplusj.OperationExecutor;
import com.xplusj.RuntimeContext;
import com.xplusj.stack.Stack;

interface StackBasedExecutor {
    OperationExecutor<? extends RuntimeContext> getOperationExecutor();
    void execute(Stack<Double> valueStack);
    boolean precedes(StackBasedExecutor executor);
}
