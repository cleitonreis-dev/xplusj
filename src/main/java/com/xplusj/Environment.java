package com.xplusj;

import com.xplusj.interpreter.ExpressionParser;

public interface Environment {

    Expression expression(String expression);

    Expression formula(String formula);

    GlobalContext getGlobalContext();

    ExpressionParser getExpressionParser();

}
