package com.xplusj.context;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.GlobalContext;
import com.xplusj.expression.FormulaExpression;
import com.xplusj.expression.InlineExpression;
import com.xplusj.interpreter.ExpressionParser;

public class DefaultEnvironment implements Environment {

    private final GlobalContext context;
    private final ExpressionParser parser;

    public DefaultEnvironment(GlobalContext context) {
        this.context = context;
        this.parser = new com.xplusj.interpreter.parser.ExpressionParser(context);
    }

    @Override
    public Expression expression(String expression) {
        return new InlineExpression(expression, context, parser);
    }

    @Override
    public Expression formula(String formula) {
        return new FormulaExpression(formula, context, parser);
    }

    @Override
    public GlobalContext getGlobalContext() {
        return context;
    }

    @Override
    public ExpressionParser getExpressionParser() {
        return parser;
    }
}
