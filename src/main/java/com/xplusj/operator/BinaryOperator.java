package com.xplusj.operator;

import com.xplusj.OperationExecutor;
import com.xplusj.OperationPrecedence;
import com.xplusj.OperationType;

import java.util.function.Function;

class BinaryOperator
        extends Operator<BinaryOperatorRuntimeContext>
        implements OperationExecutor<BinaryOperatorRuntimeContext> {

    BinaryOperator(char symbol, OperationPrecedence precedence,
                           Function<BinaryOperatorRuntimeContext, Double> function) {
        super(OperationType.BINARY_OPERATOR,symbol,precedence,function);
    }
}
