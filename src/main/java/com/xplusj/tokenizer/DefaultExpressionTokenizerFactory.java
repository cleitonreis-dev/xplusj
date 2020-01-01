package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperators;
import com.xplusj.factory.ExpressionTokenizerFactory;

public class DefaultExpressionTokenizerFactory implements ExpressionTokenizerFactory {
    private static final DefaultExpressionTokenizerFactory INSTANCE =
            new DefaultExpressionTokenizerFactory();

    @Override
    public ExpressionTokenizer create(final ExpressionOperators operatorDefinitions) {
        return DefaultExpressionTokenizer.create(operatorDefinitions);
    }

    public static DefaultExpressionTokenizerFactory getInstance(){
        return INSTANCE;
    }
}
