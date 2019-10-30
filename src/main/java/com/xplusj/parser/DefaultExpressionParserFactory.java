package com.xplusj.parser;

import com.xplusj.Environment;
import com.xplusj.ExpressionParserFactory;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    private static final DefaultExpressionParserFactory INSTANCE = new DefaultExpressionParserFactory();

    @Override
    public ExpressionParser create(Environment env) {
        return DefaultExpressionParser.create(env.getContext(), env.getTokenizer());
    }

    public static ExpressionParserFactory instance() {
        return INSTANCE;
    }
}
