package com.xplusj.parser;

import com.xplusj.operator.OperatorContext;
import com.xplusj.operator.Operator;

public interface ExpressionParserProcessor<Result> {
    void addValue(double value);

    void addVar(String value);

    void addConstant(String name);

    void addOperator(Operator<? extends OperatorContext> operator);

    void callLastOperatorAndAddResult();

    void callLastOperatorAndAddResult(int totalOfParamsToRead);

    Operator<?> getLastOperator();

    Result getResult();
}
