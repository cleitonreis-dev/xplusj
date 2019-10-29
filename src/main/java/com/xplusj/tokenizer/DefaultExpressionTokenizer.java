package com.xplusj.tokenizer;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{

    private final GlobalContext context;
    private final com.xplusj.tokenizer.TokenReader.OperatorChecker operatorChecker;

    private DefaultExpressionTokenizer(Environment env) {
        this.context = env.getContext();
        this.operatorChecker = op->context.hasBinaryOperator(op) || context.hasUnaryOperator(op);
    }

    @Override
    public TokenReader start(final String expression) {
        return new com.xplusj.tokenizer.TokenReader(expression, operatorChecker);
    }

    public static DefaultExpressionTokenizer create(Environment env){
        return new DefaultExpressionTokenizer(env);
    }
}
