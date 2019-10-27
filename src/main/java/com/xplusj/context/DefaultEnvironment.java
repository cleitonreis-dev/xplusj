package com.xplusj.context;

import com.xplusj.ContextAppender;
import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.GlobalContext;
import com.xplusj.expression.FormulaExpression;
import com.xplusj.expression.InlineExpression;
import com.xplusj.interpreter.ExpressionParser;

public class DefaultEnvironment implements Environment {

    private final ContextAppender context;
    private final ExpressionParser parser;

    private DefaultEnvironment(ContextAppender context, ExpressionParser parser) {
        this.context = context;
        this.parser = parser;
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
    public Environment appendContext(GlobalContext context) {
        ContextAppender appender = this.context.append(context);
        return new DefaultEnvironment(appender,new com.xplusj.interpreter.parser.ExpressionParser(appender));
    }

    public static Environment create(GlobalContext context){
        ContextAppender appender = DefaultContextAppender.create(context);
        return new DefaultEnvironment(appender,new com.xplusj.interpreter.parser.ExpressionParser(appender));
    }
}
