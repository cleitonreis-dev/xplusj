package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.operation.Operation;
import com.xplusj.operation.RuntimeContext;
import com.xplusj.operation.operator.BinaryOperatorRuntimeContext;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BinaryOperatorStackBasedExecutor
        implements StackBasedExecutor{

    private final Operation<BinaryOperatorRuntimeContext> operation;
    private final Environment env;

    @Override
    public Operation<? extends RuntimeContext> getOperation() {
        return operation;
    }

    @Override
    public void execute(Stack<Double> valueStack) {
        valueStack.push(operation.execute(
            new ExpressionBinaryOperatorRuntimeContext(valueStack.pull(), valueStack.pull(), env))
        );
    }

    @Override
    public boolean precedes(StackBasedExecutor executor) {
        return operation.precedes(executor.getOperation());
    }
}
