package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.ExpressionContext;
import com.xplusj.factory.ExpressionFactory;

public class DefaultExpressionFactory implements ExpressionFactory {
    private static final DefaultExpressionFactory INSTANCE = new DefaultExpressionFactory();

    @Override
    public Expression expression(final String expression, final ExpressionContext context) {
        return DefaultExpression.create(
                expression,context.getParser(),
                (varCtx)->TwoStackBasedProcessor.create(context,varCtx,Stack.instance(),Stack.instance())
        );
    }

    @Override
    public Expression formula(final String expression, final ExpressionContext context) {
        return FormulaExpression.create(expression, context.getParser(),
                (varCtx)->TwoStackBasedProcessor.create(context,varCtx),
                InstructionListProcessor::create
        );
    }

    public static DefaultExpressionFactory getInstance(){
        return INSTANCE;
    }
}
