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
    public double eval(VariableContext variableContext) {
        if(expression.trim().isEmpty())
            return 0;

        TwoStackBasedProcessor processor = processorFactory.apply(variableContext);
        parser.eval(expression, processor);

        return processor.getCalculatedResult();
    }

    public static DefaultExpression create(final String formula,
                                           final ExpressionParser parser,
                                           final Function<VariableContext,TwoStackBasedProcessor> processorFactory){
        return new DefaultExpression(formula,parser,processorFactory);
    }
}
