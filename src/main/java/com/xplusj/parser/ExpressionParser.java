package com.xplusj.parser;

public interface ExpressionParser {

    void eval(final String expression, final ExpressionParserProcessor instructionHandler);
}
