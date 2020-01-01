package com.xplusj.factory;

import com.xplusj.ExpressionOperators;
import com.xplusj.parser.DefaultExpressionParserFactory;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionParserFactory {
    ExpressionParser create(ExpressionTokenizer tokenizer, ExpressionOperators operatorDefinitions);

    static ExpressionParserFactory defaultFactory(){
        return DefaultExpressionParserFactory.getInstance();
    }
}
