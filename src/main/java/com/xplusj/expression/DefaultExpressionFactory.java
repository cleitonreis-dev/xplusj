package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.ExpressionFactory;

public class DefaultExpressionFactory implements ExpressionFactory {
    private static final DefaultExpressionFactory INSTANCE = new DefaultExpressionFactory();

    @Override
    public Expression expression(final String expression, final Environment env) {
        return DefaultExpression.create(
                expression,env.getParser(),
                (varCtx)->TwoStackBasedProcessor.create(env,varCtx,Stack.instance(),Stack.instance())
        );
    }

    @Override
    public Expression formula(final String expression, final Environment env) {
        return FormulaExpression.create(expression, env.getParser(),
                (varCtx)->TwoStackBasedProcessor.create(env,varCtx,Stack.instance(),Stack.instance()),
                ()->InstructionListProcessor.create(Stack.instance())
        );
    }

    public static DefaultExpressionFactory getInstance(){
        return INSTANCE;
    }
}
