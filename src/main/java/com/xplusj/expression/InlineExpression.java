package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.interpreter.ExpressionInterpreterProcessor;
import com.xplusj.interpreter.ExpressionParser;
import com.xplusj.interpreter.stack.Stack;

public class InlineExpression implements Expression {

    private final String expression;
    private final Environment environment;

    public InlineExpression(final String expression, final Environment environment) {
        this.expression = expression;
        this.environment = environment;
    }

    @Override
    public double eval() {
        return eval(VariableContext.EMPTY);
    }

    @Override
    public double eval(VariableContext variableContext) {
        if(expression.trim().isEmpty())
            return 0;

        Stack<Double> valueStack = Stack.defaultStack();
        ExpressionParser parser = environment.getExpressionParser();
        ExpressionInterpreterProcessor interpreter = TwoStackBasedInterpreter.create(
            environment.getGlobalContext(),
            variableContext,
            valueStack,
            Stack.defaultStack()
        );

        parser.eval(expression, interpreter);

        return valueStack.pull();
    }
}
