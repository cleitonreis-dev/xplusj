package com.xplusj.factory;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public class DefaultExpressionParserFactory implements ExpressionParserFactory {

    protected DefaultExpressionParserFactory(){}

    @Override
    public ExpressionParser create(ExpressionTokenizer tokenizer, ExpressionOperatorDefinitions operatorDefinitions) {
        return null;
    }

    public static DefaultExpressionParserFactory create(){
        return new DefaultExpressionParserFactory();
    }
}
