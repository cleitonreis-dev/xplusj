package com.xplusj.engine.parser;

import com.xplusj.core.operator.Operator;

public interface InstructionsProcessor {
    void pushValue(double value);

    void pushVar(String value);

    void pushConstant(String name);

    void pushOperator(Operator<?> operator);

    void callLastOperatorAndPushResult();

    Operator<?> getLastOperator();
}
