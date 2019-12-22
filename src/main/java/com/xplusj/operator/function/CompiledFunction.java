package com.xplusj.operator.function;

import com.xplusj.ExpressionContext;
import com.xplusj.Expression;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.VariableContext;
import com.xplusj.operator.function.FunctionOperatorContext;

import java.util.function.Function;

public class CompiledFunction implements Function<FunctionOperatorContext,Double> {

    private final String expressionStr;
    private Expression expression;

    public CompiledFunction(String expression) {
        this.expressionStr = expression;
    }

    @Override
    public Double apply(FunctionOperatorContext context) {
        if(this.expression == null){
            //this.expression = context.getEnvironment().formula(expressionStr);
        }

        return this.expression.eval(new VariableContext() {
            @Override
            public double value(String name) {
                return context.param(name);
            }

            @Override
            public boolean contains(String name) {
                return true;
            }
        });
    }

    /*public static void main(String[] args) {
        Environment env = Environment.env()
            .appendContext(GlobalContext.builder()
                .addFunction(FunctionOperator.create("test(a,b)", "pow(max(a,b),2)"))
                .addFunction(FunctionOperator.create("test2(a,b)", (ctx)->ctx.call("pow", ctx.call("max", ctx.param(0), ctx.param(1)), 2)))
                .addFunction(FunctionOperator.create("test3(a,b)", (ctx)->Math.pow(Math.max(ctx.param(0), ctx.param(1)), 2)))
                .build()
            );

        double v = -1d;
        int loop = 500;

        long start = System.currentTimeMillis();
        Expression expression = env.formula("2*test(2,3)");
        for(int i = 0; i < loop; i++)
            v = expression.eval();

        long end = System.currentTimeMillis();

        System.out.println(v);
        System.out.printf("Time %s", (end - start));

    }*/
}
