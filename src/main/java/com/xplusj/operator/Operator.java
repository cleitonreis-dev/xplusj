package com.xplusj.operator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

@AllArgsConstructor
@Getter
@ToString(of = {"type", "symbol", "precedence"})
@EqualsAndHashCode(of = {"type", "symbol"})
public class Operator {
    private final OperatorType type;
    private final char symbol;
    private final OperatorPrecedence precedence;
    private final Function<UnaryOperatorRuntimeContext,Double> function;

    public static Operator binary(char symbol, OperatorPrecedence precedence, Function<UnaryOperatorRuntimeContext,Double> function){
        return new Operator(OperatorType.BINARY,symbol,precedence,function);
    }

    public static Operator unary(char symbol, OperatorPrecedence precedence, Function<UnaryOperatorRuntimeContext,Double> function){
        return new Operator(OperatorType.UNARY,symbol,precedence,function);
    }
}
