package com.xplusj;

import com.xplusj.parser.DefaultExpressionParserFactory;
import com.xplusj.parser.ExpressionParser;

public interface ExpressionParserFactory {
    ExpressionParser create(Environment env);

    static ExpressionParserFactory defaultFactory(){
        return DefaultExpressionParserFactory.instance();
    }
}
