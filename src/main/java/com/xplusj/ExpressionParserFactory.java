package com.xplusj;

import com.xplusj.parser.DefaultExpressionParserFactory;
import com.xplusj.parser.ExpressionParser;

//TODO delete
public interface ExpressionParserFactory {
    ExpressionParser create(Environment env);

    static ExpressionParserFactory defaultFactory(){
        return null;
    }
}
