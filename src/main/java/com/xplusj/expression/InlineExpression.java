package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParser;

public class InlineExpression implements Expression {

    private final String expression;
    private final Environment env;
    private final ExpressionParser parser;

    public InlineExpression(final String expression,
                            final Environment env) {
        this.expression = expression;
        this.env = env;
        this.parser = env.getParser();
    }

    @Override
    public double eval() {
        return eval(VariableContext.EMPTY);
    }

    @Override
    public double eval(VariableContext variableContext) {
        if(expression.trim().isEmpty())
            return 0;

        TwoStackBasedInterpreter interpreter = TwoStackBasedInterpreter.create(
            env,
            variableContext,
            Stack.instance(),
            Stack.instance()
        );

        parser.eval(expression, interpreter);

        return interpreter.getCalculatedResult();
    }
}
