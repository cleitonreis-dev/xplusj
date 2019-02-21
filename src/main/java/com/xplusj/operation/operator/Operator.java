package com.xplusj.operation.operator;

import com.xplusj.operation.Operation;
import com.xplusj.operation.Precedence;
import com.xplusj.operation.OperationType;
import com.xplusj.operation.RuntimeContext;
import lombok.*;

import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(of = {"type", "symbol", "precedence"})
@EqualsAndHashCode(of = {"type", "symbol"})
public class Operator<T extends RuntimeContext> implements Operation<T> {
    private final OperationType type;
    private final char symbol;
    private final Precedence precedence;
    private final Function<T,Double> function;

    @Override
    public OperationType getOperationType() {
        return type;
    }

    @Override
    public Precedence getOperationPrecedence() {
        return precedence;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public double execute(T context) {
        return function.apply(context);
    }

    @Override
    public boolean precedes(Operation<?> executor) {
        return precedence.compareTo(executor.getOperationPrecedence()) > 0;
    }

    public static Operator<BinaryOperatorRuntimeContext> binary(char symbol, Precedence precedence, Function<BinaryOperatorRuntimeContext,Double> function){
        return new Operator<>(OperationType.BINARY_OPERATOR, symbol,precedence,function);
    }

    public static Operator<UnaryOperatorRuntimeContext> unary(char symbol, Precedence precedence, Function<UnaryOperatorRuntimeContext,Double> function){
        return new Operator<>(OperationType.UNARY_OPERATOR, symbol,precedence,function);
    }
}
