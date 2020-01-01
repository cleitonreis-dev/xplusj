package com.xplusj.operator.unary;

import com.xplusj.operator.OperatorDefinition;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;

import java.util.function.Function;

public class UnaryOperatorDefinition extends OperatorDefinition<UnaryOperatorContext> {

    protected UnaryOperatorDefinition(String symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        super(symbol, OperatorType.UNARY, precedence, function, 1);
    }

    public static UnaryOperatorDefinition unary(String symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        return new UnaryOperatorDefinition(symbol,precedence,function);
    }
}
