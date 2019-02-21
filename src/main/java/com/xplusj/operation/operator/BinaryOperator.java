package com.xplusj.operation.operator;

import com.xplusj.operation.OperationType;
import com.xplusj.operation.OperationVisitor;
import com.xplusj.operation.Precedence;

import java.util.function.Function;

public class BinaryOperator extends Operator<BinaryOperatorRuntimeContext> {

    public BinaryOperator(char symbol, Precedence precedence, Function<BinaryOperatorRuntimeContext, Double> function) {
        super(OperationType.BINARY_OPERATOR, symbol, precedence, function);
    }

    @Override
    public double accept(OperationVisitor visitor) {
        return visitor.execute(this);
    }
}
