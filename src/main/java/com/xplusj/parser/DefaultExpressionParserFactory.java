package com.xplusj.parser;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionParserFactory;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    private static final DefaultExpressionParserFactory INSTANCE = new DefaultExpressionParserFactory();

    @Override
    public ExpressionParser create(ExpressionContext context) {
        return DefaultExpressionParser.create(context.getDefinitions(), context.getTokenizer());
    }

    public static ExpressionParserFactory getInstance() {
        return INSTANCE;
    }
}
