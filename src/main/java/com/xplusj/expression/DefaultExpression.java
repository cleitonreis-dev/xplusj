package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParser;

import java.util.function.Function;

public class DefaultExpression implements Expression {

    private final String expression;
    private final ExpressionParser parser;
    private final Function<VariableContext,TwoStackBasedProcessor> processorFactory;

    private DefaultExpression(final String expression,
                              final ExpressionParser parser,
                              final Function<VariableContext,TwoStackBasedProcessor> processorFactory) {
        this.expression = expression;
        this.processorFactory = processorFactory;
        this.parser = parser;
    }

    @Override
    public double eval() {
        return eval(VariableContext.EMPTY);
    }

    @Override
    public double eval(final VariableContext variableContext) {
        TwoStackBasedProcessor processor = processorFactory.apply(variableContext);
        parser.eval(expression, processor);
        return processor.getCalculatedResult();
    }

    public static DefaultExpression create(final String expression,
                                           final ExpressionParser parser,
                                           final Function<VariableContext,TwoStackBasedProcessor> processorFactory){
        if(expression == null)
            throw new ExpressionException("Invalid expression: expression null");

        if(expression.trim().isEmpty())
            throw new ExpressionException("Invalid expression: expression empty");

        return new DefaultExpression(expression,parser,processorFactory);
    }
}
