package com.xplusj.operator;

public interface Operator<T extends OperatorContext> {

    OperatorType getType();

    Precedence getPrecedence();

    int getParamsLength();

    double execute(T context);

    boolean precedes(Operator<?> operator);
}
