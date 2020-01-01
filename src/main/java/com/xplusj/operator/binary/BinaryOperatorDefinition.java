package com.xplusj.operator.binary;

import com.xplusj.operator.OperatorDefinition;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;

import java.util.function.Function;

public class BinaryOperatorDefinition extends OperatorDefinition<BinaryOperatorContext> {

    protected BinaryOperatorDefinition(String symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        super(symbol, OperatorType.BINARY, precedence, function, 2);
    }

    public static BinaryOperatorDefinition binary(String symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        return new BinaryOperatorDefinition(symbol,precedence,function);
    }
}
