package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperators;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{

    private final ExpressionOperators operatorDefinitions;

    private DefaultExpressionTokenizer(final ExpressionOperators operatorDefinitions) {
        this.operatorDefinitions = operatorDefinitions;

    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.tokenizer.Tokenizer(expression, operatorDefinitions);
    }

    public static DefaultExpressionTokenizer create(ExpressionOperators operatorDefinitions){
        return new DefaultExpressionTokenizer(operatorDefinitions);
    }
}
