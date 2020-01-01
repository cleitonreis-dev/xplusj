package com.xplusj.operator.unary;

import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;

import java.util.function.Function;

public class UnaryOperator extends Operator<UnaryOperatorContext> {

    protected UnaryOperator(String symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        super(symbol, OperatorType.UNARY, precedence, function, 1);
    }

    public static UnaryOperator unary(String symbol, Precedence precedence, Function<UnaryOperatorContext, Double> function) {
        return new UnaryOperator(symbol,precedence,function);
    }
}
