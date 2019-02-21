package com.xplusj.operation.operator;

import com.xplusj.operation.OperationType;
import com.xplusj.operation.OperationVisitor;
import com.xplusj.operation.Precedence;

import java.util.function.Function;

public class UnaryOperator extends Operator<UnaryOperatorRuntimeContext> {

    public UnaryOperator(char symbol, Precedence precedence, Function<UnaryOperatorRuntimeContext, Double> function) {
        super(OperationType.UNARY_OPERATOR, symbol, precedence, function);
    }

    @Override
    public double accept(OperationVisitor visitor) {
        return visitor.execute(this);
    }
}
