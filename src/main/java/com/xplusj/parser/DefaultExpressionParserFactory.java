package com.xplusj.parser;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.factory.ExpressionParserFactory;
import com.xplusj.tokenizer.ExpressionTokenizer;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    private static final DefaultExpressionParserFactory INSTANCE = new DefaultExpressionParserFactory();

    @Override
    public ExpressionParser create(ExpressionTokenizer tokenizer, ExpressionOperatorDefinitions operatorDefinitions) {
        return DefaultExpressionParser.create(operatorDefinitions, tokenizer);
    }

    public static ExpressionParserFactory getInstance() {
        return INSTANCE;
    }
}
