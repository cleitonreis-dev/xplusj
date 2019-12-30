package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperatorDefinitions;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{

    private final ExpressionOperatorDefinitions operatorDefinitions;

    private DefaultExpressionTokenizer(final ExpressionOperatorDefinitions operatorDefinitions) {
        this.operatorDefinitions = operatorDefinitions;

    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.tokenizer.Tokenizer(expression, operatorDefinitions);
    }

    public static DefaultExpressionTokenizer create(ExpressionOperatorDefinitions operatorDefinitions){
        return new DefaultExpressionTokenizer(operatorDefinitions);
    }
}
