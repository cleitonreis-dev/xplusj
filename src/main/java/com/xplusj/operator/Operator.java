package com.xplusj.operator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@ToString(of = {"type", "precedence"})
@EqualsAndHashCode(of = {"type"})
public abstract class Operator<T extends OperatorContext>{
    private final OperatorType type;
    private final Precedence precedence;
    final Function<T,Double> function;

    public OperatorType getType() {
        return type;
    }

    public Precedence getPrecedence() {
        return precedence;
    }

    public boolean precedes(Operator<?> operator) {
        return precedence.compareTo(operator.getPrecedence()) > 0;
    }

    public abstract int getParamsLength();

    public abstract double execute(T context);
}
