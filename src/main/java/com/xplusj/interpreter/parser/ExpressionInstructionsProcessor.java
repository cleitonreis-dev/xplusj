package com.xplusj.interpreter.parser;

import com.xplusj.operator.Operator;

public interface ExpressionInstructionsProcessor {
    void pushValue(double value);

    void pushVar(String value);

    void pushConstant(String name);

    void pushOperator(Operator<?> operator);

    void callLastOperatorAndPushResult();

    Operator<?> getLastOperator();
}
