package com.xplusj.operation.operator;

import com.xplusj.operation.Operation;
import com.xplusj.operation.Precedence;
import com.xplusj.operation.OperationType;
import com.xplusj.operation.RuntimeContext;
import lombok.*;

import java.util.function.Function;

@AllArgsConstructor
@ToString(of = {"type", "symbol", "precedence"})
@EqualsAndHashCode(of = {"type", "symbol"})
public abstract class Operator<T extends RuntimeContext> implements Operation<T> {
    private final OperationType type;
    private final char symbol;
    private final Precedence precedence;
    private final Function<T,Double> function;

    @Override
    public OperationType geType() {
        return type;
    }

    @Override
    public Precedence getPrecedence() {
        return precedence;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public boolean precedes(Operation<?> executor) {
        return precedence.compareTo(executor.getPrecedence()) > 0;
    }

    @Override
    public double execute(T context) {
        return function.apply(context);
    }

    public static BinaryOperator binary(char symbol, Precedence precedence, Function<BinaryOperatorRuntimeContext,Double> function){
        return new BinaryOperator(symbol,precedence,function);
    }

    public static UnaryOperator unary(char symbol, Precedence precedence, Function<UnaryOperatorRuntimeContext,Double> function){
        return new UnaryOperator(symbol,precedence,function);
    }
}
