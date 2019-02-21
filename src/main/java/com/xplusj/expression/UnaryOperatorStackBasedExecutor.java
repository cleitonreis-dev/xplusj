package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.operation.Operation;
import com.xplusj.operation.RuntimeContext;
import com.xplusj.operation.operator.UnaryOperatorRuntimeContext;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnaryOperatorStackBasedExecutor
        implements StackBasedExecutor{

    private final Operation<UnaryOperatorRuntimeContext> operation;
    private final Environment env;

    @Override
    public Operation<? extends RuntimeContext> getOperation() {
        return operation;
    }

    @Override
    public void execute(Stack<Double> valueStack) {
        valueStack.push(operation.execute(
            new ExpressionUnaryOperatorRuntimeContext(valueStack.pull(), env))
        );
    }

    @Override
    public boolean precedes(StackBasedExecutor executor) {
        return operation.precedes(executor.getOperation());
    }
}
