package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.OperationExecutor;
import com.xplusj.RuntimeContext;
import com.xplusj.function.ExpressionFunction;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class FunctionStackBasedExecutor
        implements StackBasedExecutor{

    private final ExpressionFunction function;
    private final Environment env;

    @Override
    public OperationExecutor<? extends RuntimeContext> getOperationExecutor() {
        return function;
    }

    @Override
    public void execute(Stack<Double> valueStack) {
        String[] params = function.getParams();
        Map<String,Double> paramsMap = new HashMap<>(params.length);

        for(int i = params.length - 1; i >= 0; i--)
            paramsMap.put(params[i], valueStack.pull());

        valueStack.push(function.execute(new ExpressionFunctionRuntimeContext(paramsMap, env)));
    }

    @Override
    public boolean precedes(StackBasedExecutor executor) {
        return function.precedes(executor.getOperationExecutor());
    }
}
