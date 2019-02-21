package com.xplusj.operation;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.BinaryOperator;
import com.xplusj.operation.operator.UnaryOperator;

public interface OperationVisitor {

    double execute(ExpressionFunction function);

    double execute(UnaryOperator unaryOperator);

    double execute(BinaryOperator binaryOperator);
}
