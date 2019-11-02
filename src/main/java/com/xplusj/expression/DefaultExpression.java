package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParser;

public class DefaultExpression implements Expression {

    private final String expression;
    private final Environment env;
    private final ExpressionParser parser;

    private DefaultExpression(final String expression,
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

        TwoStackBasedProcessor interpreter = TwoStackBasedProcessor.create(
            env,
            variableContext,
            Stack.instance(),
            Stack.instance()
        );

        parser.eval(expression, interpreter);

        return interpreter.getCalculatedResult();
    }

    public static DefaultExpression create(final String formula, final Environment env){
        return new DefaultExpression(formula,env);
    }
}
