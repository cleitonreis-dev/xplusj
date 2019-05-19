package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.interpreter.ExpressionInterpreterProcessor;
import com.xplusj.interpreter.ExpressionParser;
import com.xplusj.interpreter.stack.Stack;

public class InlineExpression implements Expression {

    private final String expression;
    private final GlobalContext context;
    private final ExpressionParser parser;

    public InlineExpression(final String expression, final GlobalContext context, final ExpressionParser parser) {
        this.expression = expression;
        this.context = context;
        this.parser = parser;
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
            context,
            variableContext,
            Stack.defaultStack(),
            Stack.defaultStack()
        );

        parser.eval(expression, interpreter);

        return interpreter.getCalculatedResult();
    }
}
