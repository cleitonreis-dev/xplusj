package com.xplusj.operator;

import com.xplusj.core.operator.Operator;
import com.xplusj.core.operator.OperatorContext;
import com.xplusj.core.operator.OperatorType;
import com.xplusj.core.operator.Precedence;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@ToString(of = {"type", "symbol", "precedence"})
@EqualsAndHashCode(of = {"type", "symbol"})
public abstract class AbstractOperator<T extends OperatorContext> implements Operator<T> {
    private final OperatorType type;
    private final char symbol;
    private final Precedence precedence;
    final Function<T,Double> function;

    @Override
    public OperatorType geType() {
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
    public boolean precedes(Operator<?> operator) {
        return precedence.compareTo(operator.getPrecedence()) > 0;
    }
}