package com.xplusj.interpreter;

import com.xplusj.operator.Operator;

public interface ExpressionInterpreterProcessor {
    void pushValue(double value);

    void pushVar(String value);

    void pushConstant(String name);

    void pushOperator(Operator<?> operator);

    void callLastOperatorAndPushResult();

    Operator<?> getLastOperator();
}
