package com.xplusj.parser;

import com.xplusj.operator.OperatorContext;
import com.xplusj.operator.OperatorDefinition;

public interface ExpressionParserProcessor<Result> {
    void addValue(double value);

    void addVar(String value);

    void addConstant(String name);

    void addOperator(OperatorDefinition<? extends OperatorContext> operator);

    void callLastOperatorAndAddResult();

    void callLastOperatorAndAddResult(int totalOfParamsToRead);

    OperatorDefinition<?> getLastOperator();

    Result getResult();
}
