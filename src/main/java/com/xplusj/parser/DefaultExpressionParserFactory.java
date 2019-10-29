package com.xplusj.parser;

import com.xplusj.Environment;
import com.xplusj.ExpressionParserFactory;

import com.xplusj.tokenizer.ExpressionTokenizer;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    private static final DefaultExpressionParserFactory INSTANCE = new DefaultExpressionParserFactory();

    @Override
    public ExpressionParser create(Environment env, ExpressionTokenizer tokenizer) {
        return DefaultExpressionParser.create(env,tokenizer);
    }

    public static ExpressionParserFactory instance() {
        return INSTANCE;
    }
}
