package com.xplusj.operator.binary;

import com.xplusj.operator.OperatorDefinition;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@ToString(of = {"symbol"},callSuper = true)
@EqualsAndHashCode(of = {"symbol"}, callSuper = true)
public class BinaryOperatorDefinition extends OperatorDefinition<BinaryOperatorContext> {
    private final char symbol;

    protected BinaryOperatorDefinition(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        super(OperatorType.BINARY, precedence, function, 2);
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static BinaryOperatorDefinition create(char symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        return new BinaryOperatorDefinition(symbol,precedence,function);
    }
}
