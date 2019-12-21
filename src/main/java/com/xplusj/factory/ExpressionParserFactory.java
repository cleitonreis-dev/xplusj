package com.xplusj.factory;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionParserFactory {
    ExpressionParser create(ExpressionTokenizer tokenizer, ExpressionOperatorDefinitions operatorDefinitions);

    static ExpressionParserFactory defaultFactory(){
        return DefaultExpressionParserFactory.create();
    }
}
