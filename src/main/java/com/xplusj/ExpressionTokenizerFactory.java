package com.xplusj;

import com.xplusj.tokenizer.DefaultExpressionTokenizerFactory;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionTokenizerFactory {
    ExpressionTokenizer create(Environment env);

    static ExpressionTokenizerFactory defaultFactory(){
        return DefaultExpressionTokenizerFactory.instance();
    }
}
