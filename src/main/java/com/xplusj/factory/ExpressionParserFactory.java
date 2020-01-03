package com.xplusj.factory;

import com.xplusj.ExpressionContext;
import com.xplusj.parser.DefaultExpressionParserFactory;
import com.xplusj.parser.ExpressionParser;

public interface ExpressionParserFactory {
    ExpressionParser create(ExpressionContext context);

    static ExpressionParserFactory defaultFactory(){
        return DefaultExpressionParserFactory.getInstance();
    }
}
