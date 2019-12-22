package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperatorDefinitions;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{

    private final com.xplusj.tokenizer.Tokenizer.OperatorChecker operatorChecker;

    private DefaultExpressionTokenizer(final ExpressionOperatorDefinitions operatorDefinitions) {
        this.operatorChecker = op->operatorDefinitions.hasBinaryOperator(op)
                || operatorDefinitions.hasUnaryOperator(op);
    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.tokenizer.Tokenizer(expression, operatorChecker);
    }

    public static DefaultExpressionTokenizer create(ExpressionOperatorDefinitions operatorDefinitions){
        return new DefaultExpressionTokenizer(operatorDefinitions);
    }
}
