package com.xplusj.tokenizer;

public interface ExpressionTokenizer {

    TokenReader start(String expression);

    interface TokenReader{
        Token getLastToken();

        boolean hasNext();

        Token next();

        String getExpression();
    }
}
