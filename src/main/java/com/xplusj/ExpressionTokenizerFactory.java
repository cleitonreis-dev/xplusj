package com.xplusj;

import com.xplusj.tokenizer.DefaultExpressionTokenizerFactory;
import com.xplusj.tokenizer.ExpressionTokenizer;

//TODO delete
public interface ExpressionTokenizerFactory {
    ExpressionTokenizer create(Environment env);

    static ExpressionTokenizerFactory defaultFactory(){
        return null;
    }
}
