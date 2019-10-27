package com.xplusj.operator;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@ToString(of = {"symbol"},callSuper = true)
@EqualsAndHashCode(of = {"symbol"}, callSuper = true)
public abstract class UBOperator<T extends OperatorContext> extends Operator<T>{
    private final char symbol;

    public UBOperator(OperatorType type, char symbol, Precedence precedence, Function<T, Double> function) {
        super(type, precedence, function);
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
