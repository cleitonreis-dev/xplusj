package com.xplusj.tokenizer;

import com.xplusj.GlobalContext;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{

    private final GlobalContext context;
    private final com.xplusj.tokenizer.Tokenizer.OperatorChecker operatorChecker;

    private DefaultExpressionTokenizer(GlobalContext context) {
        this.context = context;
        this.operatorChecker = op->context.hasBinaryOperator(op) || context.hasUnaryOperator(op);
    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.tokenizer.Tokenizer(expression, operatorChecker);
    }

    public static DefaultExpressionTokenizer create(GlobalContext context){
        return new DefaultExpressionTokenizer(context);
    }
}
