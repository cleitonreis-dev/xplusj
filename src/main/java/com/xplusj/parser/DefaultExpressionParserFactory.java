package com.xplusj.parser;

import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionParserFactory;
import com.xplusj.parser.tokenizer.ExpressionTokenizer;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    private static final DefaultExpressionParserFactory INSTANCE = new DefaultExpressionParserFactory();

    @Override
    public ExpressionParser create(ExpressionContext context) {
        return DefaultExpressionParser.create(context.getDefinitions(), createTokenizer(context));
    }

    protected ExpressionTokenizer createTokenizer(ExpressionContext context){
        return ExpressionTokenizer.tokenizer(context);
    }

    public static ExpressionParserFactory getInstance() {
        return INSTANCE;
    }
}
