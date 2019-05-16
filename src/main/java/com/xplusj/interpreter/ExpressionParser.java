package com.xplusj.interpreter;

public interface ExpressionParser {

    void eval(final String expression, final ExpressionInterpreterProcessor instructionHandler);
}
