package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.OperationExecutor;
import com.xplusj.RuntimeContext;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operator.UnaryOperatorRuntimeContext;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnaryOperatorStackBasedExecutor
        implements StackBasedExecutor{

    private final OperationExecutor<UnaryOperatorRuntimeContext> operationExecutor;
    private final Environment env;

    @Override
    public OperationExecutor<? extends RuntimeContext> getOperationExecutor() {
        return operationExecutor;
    }

    @Override
    public void execute(Stack<Double> valueStack) {
        valueStack.push(operationExecutor.execute(
            new ExpressionUnaryOperatorRuntimeContext(valueStack.pull(), env))
        );
    }

    @Override
    public boolean precedes(StackBasedExecutor executor) {
        return operationExecutor.precedes(executor.getOperationExecutor());
    }
}
