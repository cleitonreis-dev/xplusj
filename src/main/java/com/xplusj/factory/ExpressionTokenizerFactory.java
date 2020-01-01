package com.xplusj.factory;

import com.xplusj.ExpressionOperators;
import com.xplusj.tokenizer.DefaultExpressionTokenizerFactory;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionTokenizerFactory {
    ExpressionTokenizer create(ExpressionOperators operatorDefinitions);

    static ExpressionTokenizerFactory defaultFactory(){
        return DefaultExpressionTokenizerFactory.getInstance();
    }
}
