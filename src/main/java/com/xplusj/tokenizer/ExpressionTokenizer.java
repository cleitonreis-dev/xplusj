package com.xplusj.tokenizer;

public interface ExpressionTokenizer {

    Tokenizer tokenize(String expression);

    interface Tokenizer {
        Token getLastToken();

        boolean hasNext();

        Token next();

        String getExpression();
    }
}
