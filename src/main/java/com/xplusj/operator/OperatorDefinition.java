package com.xplusj.operator;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
@ToString(of = {"identifier","type", "precedence", "paramsLength"})
public abstract class OperatorDefinition <T extends OperatorContext>{
    private final String identifier;
    private final OperatorType type;
    private final Precedence precedence;
    private final Function<T,Double> function;
    private final int paramsLength;

    public String getIdentifier() {
        return identifier;
    }

    public OperatorType getType() {
        return type;
    }

    public Precedence getPrecedence() {
        return precedence;
    }

    public boolean precedes(OperatorDefinition<?> operator) {
        return precedence.compareTo(operator.getPrecedence()) > 0;
    }

    public int getParamsLength(){
        return paramsLength;
    }

    public Function<T, Double> getFunction(){
        return function;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorDefinition<?> that = (OperatorDefinition<?>) o;
        return paramsLength == that.paramsLength &&
                Objects.equals(identifier, that.identifier) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, type, paramsLength);
    }
}
