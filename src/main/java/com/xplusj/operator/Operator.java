package com.xplusj.operator;

import com.xplusj.OperationExecutor;
import com.xplusj.OperationPrecedence;
import com.xplusj.OperationType;
import com.xplusj.RuntimeContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(of = {"type", "symbol", "precedence"})
@EqualsAndHashCode(of = {"type", "symbol"})
public class Operator<T extends RuntimeContext> implements OperationExecutor<T> {
    private final OperationType type;
    private final char symbol;
    private final OperationPrecedence precedence;
    private final Function<T,Double> function;

    @Override
    public OperationType getOperationType() {
        return type;
    }

    @Override
    public OperationPrecedence getOperationPrecedence() {
        return precedence;
    }

    @Override
    public double execute(T context) {
        return function.apply(context);
    }

    @Override
    public boolean precedes(OperationExecutor<?> executor) {
        return precedence.compareTo(executor.getOperationPrecedence()) > 0;
    }

    public static Operator<BinaryOperatorRuntimeContext> binary(char symbol, OperationPrecedence precedence, Function<BinaryOperatorRuntimeContext,Double> function){
        return new Operator<>(OperationType.BINARY_OPERATOR, symbol,precedence,function);
    }

    public static Operator<UnaryOperatorRuntimeContext> unary(char symbol, OperationPrecedence precedence, Function<UnaryOperatorRuntimeContext,Double> function){
        return new Operator<>(OperationType.UNARY_OPERATOR, symbol,precedence,function);
    }
}
