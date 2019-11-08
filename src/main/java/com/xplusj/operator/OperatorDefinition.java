package com.xplusj.operator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@ToString(of = {"type", "precedence"})
@EqualsAndHashCode(of = {"type"})
public class OperatorDefinition <T extends OperatorContext>{
    private final OperatorType type;
    private final Precedence precedence;
    private final Function<T,Double> function;
    private final int paramsLength;

    public OperatorType getType() {
        return type;
    }

    public Precedence getPrecedence() {
        return precedence;
    }

    public boolean precedes(Operator<?> operator) {
        return precedence.compareTo(operator.getDefinition().getPrecedence()) > 0;
    }

    public int getParamsLength(){
        return paramsLength;
    }

    public Function<T, Double> getFunction(){
        return function;
    }
}
