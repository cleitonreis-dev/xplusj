package com.xplusj.operator;

import java.util.Objects;
import java.util.function.Function;

public abstract class Operator<T extends OperatorContext>{
    private final String identifier;
    private final OperatorType type;
    private final Precedence precedence;
    private final Function<T,Double> function;
    private final int paramsLength;

    public Operator(String identifier, OperatorType type, Precedence precedence,
                    Function<T, Double> function, int paramsLength) {
        this.identifier = identifier;
        this.type = type;
        this.precedence = precedence;
        this.function = function;
        this.paramsLength = paramsLength;
    }

    public String getIdentifier() {
        return identifier;
    }

    public OperatorType getType() {
        return type;
    }

    public Precedence getPrecedence() {
        return precedence;
    }

    public boolean precedes(Operator<?> operator) {
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
        Operator<?> that = (Operator<?>) o;
        return paramsLength == that.paramsLength &&
                Objects.equals(identifier, that.identifier) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, type, paramsLength);
    }

    @Override
    public String toString() {
        return "Operator{" +
                "identifier='" + identifier + '\'' +
                ", type=" + type +
                ", precedence=" + precedence +
                ", paramsLength=" + paramsLength +
                '}';
    }
}
