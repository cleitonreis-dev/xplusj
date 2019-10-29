package com.xplusj;

import com.xplusj.parser.ExpressionParser;
import com.xplusj.parser.DefaultExpressionParserFactory;

import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionParserFactory {
    ExpressionParser create(Environment env, ExpressionTokenizer tokenizer);

    static ExpressionParserFactory defaultFactory(){
        return DefaultExpressionParserFactory.instance();
    }
}
