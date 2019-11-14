package com.xplusj.parser;

import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorContext;

public interface ExpressionParserProcessor<Result> {
    void addValue(double value);

    void addVar(String value);

    void addConstant(String name);

    void addOperator(Operator<? extends OperatorContext> operator);

    void callLastOperatorAndAddResult();

    Operator<?> getLastOperator();

    Result getResult();
}
