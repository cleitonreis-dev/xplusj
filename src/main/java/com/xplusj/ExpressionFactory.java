package com.xplusj;

public interface ExpressionFactory {

    FormulaExpression formula(String formulaExpression);

    ExpressionEvaluator expession(String expression);
}
