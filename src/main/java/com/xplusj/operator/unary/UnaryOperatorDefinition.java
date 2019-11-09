package com.xplusj.operator.unary;

import com.xplusj.operator.OperatorDefinition;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@ToString(of = {"symbol"},callSuper = true)
@EqualsAndHashCode(of = {"symbol"}, callSuper = true)
public class UnaryOperatorDefinition extends OperatorDefinition<UnaryOperatorContext> {
    private final char symbol;

    protected UnaryOperatorDefinition(char symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        super(OperatorType.BINARY, precedence, function, 1);
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static UnaryOperatorDefinition create(char symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        return new UnaryOperatorDefinition(symbol,precedence,function);
    }
}
