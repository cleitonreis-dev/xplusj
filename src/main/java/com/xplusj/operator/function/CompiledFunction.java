package com.xplusj.operator.function;

import com.xplusj.Expression;
import com.xplusj.ExpressionContext;
import com.xplusj.VariableContext;

import java.util.function.Function;

public class CompiledFunction implements Function<FunctionOperatorContext,Double> {

    private final String expressionStr;
    private Expression expression;

    CompiledFunction(String expression) {
        this.expressionStr = expression;
    }

    @Override
    public Double apply(FunctionOperatorContext context) {
        if(this.expression == null)
            initExpression(context.getContext());

        return this.expression.eval(new FVariableContext(context));
    }

    private synchronized void initExpression(ExpressionContext context){
        if(expression == null)
            this.expression = context.formula(expressionStr);
    }

    private static class FVariableContext implements VariableContext{
        private final FunctionOperatorContext context;

        FVariableContext(FunctionOperatorContext context) {
            this.context = context;
        }

        @Override
        public double value(String name) {
            return context.param(name);
        }

        @Override
        public boolean contains(String name) {
            return true;
        }
    }
}
