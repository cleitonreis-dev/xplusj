package com.xplusj.expression;

import com.xplusj.OperationExecutor;
import com.xplusj.RuntimeContext;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BinaryOperatorStackBasedExecutor
        implements StackBasedExecutor{

    private final OperationExecutor<BinaryOperatorRuntimeContext> operationExecutor;

    @Override
    public OperationExecutor<? extends RuntimeContext> getOperationExecutor() {
        return operationExecutor;
    }

    @Override
    public void execute(Stack<Double> valueStack) {
        valueStack.push(operationExecutor.execute(
                new ExpressionBinaryOperatorRuntimeContext(valueStack.pull(), valueStack.pull(), null))
        );
    }

    @Override
    public boolean precedes(StackBasedExecutor executor) {
        return operationExecutor.precedes(executor.getOperationExecutor());
    }
}
