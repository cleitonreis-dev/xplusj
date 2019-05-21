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

    private DefaultEnvironment(GlobalContext context) {
        this(DefaultContextAppender.create(context),
            new com.xplusj.interpreter.parser.ExpressionParser(context));
    }

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
        return new DefaultEnvironment(this.context.append(context),this.parser);
    }

    public static Environment create(GlobalContext context){
        return new DefaultEnvironment(context);
    }
}
