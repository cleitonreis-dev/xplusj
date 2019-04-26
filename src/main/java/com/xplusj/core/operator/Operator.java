package com.xplusj.core.operator;

public interface Operator<T extends OperatorContext> {

    OperatorType geType();

    Precedence getPrecedence();

    int getParamsLength();

    double execute(T context);

    boolean precedes(Operator<?> operator);
}
