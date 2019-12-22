package com.xplusj.factory;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.tokenizer.DefaultExpressionTokenizerFactory;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionTokenizerFactory {
    ExpressionTokenizer create(ExpressionOperatorDefinitions operatorDefinitions);

    static ExpressionTokenizerFactory defaultFactory(){
        return DefaultExpressionTokenizerFactory.getInstance();
    }
}
