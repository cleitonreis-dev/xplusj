package com.xplusj.factory;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.tokenizer.ExpressionTokenizer;

public class DefaultExpressionTokenizerFactory implements ExpressionTokenizerFactory {

    protected DefaultExpressionTokenizerFactory(){}

    @Override
    public ExpressionTokenizer create(ExpressionOperatorDefinitions operatorDefinitions) {
        return null;
    }

    public static DefaultExpressionTokenizerFactory create(){
        return new DefaultExpressionTokenizerFactory();
    }
}
