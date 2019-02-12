package com.xplusj.function;

import com.xplusj.OperationExecutor;
import com.xplusj.OperationPrecedence;
import com.xplusj.OperationType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "params"})
@Getter
public class ExpressionFunction implements OperationExecutor<FunctionRuntimeContext> {
    private static final OperationPrecedence PRECEDENCE =
            OperationPrecedence.lowerThan(OperationPrecedence.higher());

    private final String name;
    private final String[] params;
    private final Function<FunctionRuntimeContext, Double> function;

    @Override
    public OperationType getOperationType() {
        return OperationType.FUNCTION;
    }

    @Override
    public OperationPrecedence getOperationPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public boolean precedes(OperationExecutor<?> executor) {
        return 1 == PRECEDENCE.compareTo(executor.getOperationPrecedence());
    }

    @Override
    public double execute(FunctionRuntimeContext context) {
        return function.apply(context);
    }
}
