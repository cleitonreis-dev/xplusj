package com.xplusj;

public interface ExpressionFactory {

    FormulaExpression formula(final String formulaExpression);

    ExpressionEvaluator expression(final String expression);

    static ExpressionFactory defaultFactory(){
        return defaultFactory(Environment.defaultEnv().build());
    }

    static ExpressionFactory defaultFactory(Environment environment){
        return DefaultExpressionFactory.builder()
                .environment(environment)
                .build();
    }
}
