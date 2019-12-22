package com.xplusj.parser;

public interface ExpressionParser {

    <ParserResult> ParserResult eval(final String expression, final ExpressionParserProcessor<ParserResult> instructionHandler);
}
