package com.xplusj.expression;

import com.xplusj.Expression;
import com.xplusj.VariableContext;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.parser.ExpressionParserProcessor;

import java.util.function.Function;

public class DefaultExpression implements Expression {

    private final String expression;
    private final ExpressionParser parser;
    private final Function<VariableContext, ExpressionParserProcessor<Double>> processorFactory;

    private DefaultExpression(final String expression,
                              final ExpressionParser parser,
                              final Function<VariableContext,ExpressionParserProcessor<Double>> processorFactory) {
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
        ExpressionParserProcessor<Double> processor = processorFactory.apply(variableContext);
        return parser.eval(expression, processor);
    }

    public static DefaultExpression create(final String expression,
                                           final ExpressionParser parser,
                                           final Function<VariableContext,ExpressionParserProcessor<Double>> processorFactory){
        if(expression == null)
            throw new ExpressionException("Invalid expression: expression null");

        if(expression.trim().isEmpty())
            throw new ExpressionException("Invalid expression: expression empty");

        return new DefaultExpression(expression,parser,processorFactory);
    }
}
