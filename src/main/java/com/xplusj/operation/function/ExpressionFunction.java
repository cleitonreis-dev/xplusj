package com.xplusj.operation.function;

import com.xplusj.operation.Operation;
import com.xplusj.operation.OperationVisitor;
import com.xplusj.operation.Precedence;
import com.xplusj.operation.OperationType;
import lombok.*;

import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "params"})
@Getter
public class ExpressionFunction implements Operation<FunctionRuntimeContext> {
    private static final Precedence PRECEDENCE =
            Precedence.lowerThan(Precedence.highest());

    private final String name;
    private final String[] params;
    private final Function<FunctionRuntimeContext, Double> function;

    @Override
    public OperationType geType() {
        return OperationType.FUNCTION;
    }

    @Override
    public Precedence getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public boolean precedes(Operation<?> executor) {
        return PRECEDENCE.compareTo(executor.getPrecedence()) > 0;
    }

    @Override
    public double accept(OperationVisitor visitor) {
        return visitor.execute(this);
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
