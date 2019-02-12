package com.xplusj.operator;

import com.xplusj.OperationExecutor;
import com.xplusj.OperationPrecedence;
import com.xplusj.OperationType;

import java.util.function.Function;

class UnaryOperator
        extends Operator<UnaryOperatorRuntimeContext>
        implements OperationExecutor<UnaryOperatorRuntimeContext> {

    UnaryOperator(char symbol, OperationPrecedence precedence,
                           Function<UnaryOperatorRuntimeContext, Double> function) {
        super(OperationType.UNARY_OPERATOR,symbol,precedence,function);
    }
}
