package com.xplusj.operator.binary;

import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;

import java.util.function.Function;

public class BinaryOperator extends Operator<BinaryOperatorContext> {

    protected BinaryOperator(String symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        super(symbol, OperatorType.BINARY, precedence, function, 2);
    }

    public static BinaryOperator binary(String symbol, Precedence precedence, Function<BinaryOperatorContext, Double> function) {
        return new BinaryOperator(symbol,precedence,function);
    }
}
