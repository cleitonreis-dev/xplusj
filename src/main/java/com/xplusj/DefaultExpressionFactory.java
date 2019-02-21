package com.xplusj;

import com.xplusj.expression.Expression;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class DefaultExpressionFactory implements ExpressionFactory {

    private final Environment environment;

    @Override
    public FormulaExpression formula(final String formulaExpression) {
        return null;
    }

    @Override
    public ExpressionEvaluator expression(final String expression) {
        return new Expression(environment, expression);
    }
}
