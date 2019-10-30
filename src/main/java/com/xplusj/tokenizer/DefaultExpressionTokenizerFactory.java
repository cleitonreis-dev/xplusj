package com.xplusj.tokenizer;

import com.xplusj.Environment;
import com.xplusj.ExpressionTokenizerFactory;

public class DefaultExpressionTokenizerFactory implements ExpressionTokenizerFactory {
    private static final DefaultExpressionTokenizerFactory INSTANCE =
            new DefaultExpressionTokenizerFactory();

    @Override
    public ExpressionTokenizer create(final Environment env) {
        return DefaultExpressionTokenizer.create(env.getContext());
    }

    public static DefaultExpressionTokenizerFactory instance(){
        return INSTANCE;
    }
}
