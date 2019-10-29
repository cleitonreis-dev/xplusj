package com.xplusj;


import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionFactory {
    Expression expression(Environment env, ExpressionTokenizer tokenizer, ExpressionParser parser);

    Expression formula(Environment env, ExpressionTokenizer tokenizer, ExpressionParser parser);
}
