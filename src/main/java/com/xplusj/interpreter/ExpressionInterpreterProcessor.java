package com.xplusj.interpreter;

import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorContext;

public interface ExpressionInterpreterProcessor {
    void pushValue(double value);

    void pushVar(String value);

    void pushConstant(String name);

    void pushOperator(Operator<? extends OperatorContext> operator);

    void callLastOperatorAndPushResult();

    Operator<?> getLastOperator();
}
