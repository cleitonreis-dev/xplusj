package com.xplusj.function;

import com.xplusj.OperationExecutor;
import com.xplusj.OperationPrecedence;
import com.xplusj.OperationType;
import lombok.*;

import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static ExpressionFunction function(String nameAndParams, Function<FunctionRuntimeContext, Double> function){
        int openingParenthesis = nameAndParams.indexOf('(');
        int closingParenthesis = nameAndParams.indexOf(')');

        //TODO validate name and parenthesis

        String name = nameAndParams.substring(0, openingParenthesis);
        String[] params = nameAndParams.substring(openingParenthesis + 1, closingParenthesis).split(",");

        return new ExpressionFunction(name,params,function);
    }
}
