package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.operation.OperationVisitor;
import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.function.FunctionRuntimeContext;
import com.xplusj.operation.operator.BinaryOperator;
import com.xplusj.operation.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operation.operator.UnaryOperator;
import com.xplusj.operation.operator.UnaryOperatorRuntimeContext;
import com.xplusj.stack.Stack;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
class OperationValueStackVisitor implements OperationVisitor {
    private final Environment environment;
    private final Stack<Double> valueStack;

    @Override
    public double execute(ExpressionFunction function) {
        String[] params = function.getParams();
        Map<String,Double> paramsMap = new HashMap<>(params.length);

        for(int i = params.length - 1; i >= 0; i--)
            paramsMap.put(params[i], valueStack.pull());

        return function.getFunction().apply(new FunctionRuntimeContext(paramsMap, environment));
    }

    @Override
    public double execute(UnaryOperator unaryOperator) {
        return unaryOperator.getFunction().apply(
            new UnaryOperatorRuntimeContext(valueStack.pull(), environment));
    }

    @Override
    public double execute(BinaryOperator binaryOperator) {
        double secondValue = valueStack.pull();
        double firstValue = valueStack.pull();

        return binaryOperator.getFunction().apply(
            new BinaryOperatorRuntimeContext(firstValue,secondValue,environment));
    }
}
