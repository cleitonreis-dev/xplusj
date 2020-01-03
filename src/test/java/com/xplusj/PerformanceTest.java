package com.xplusj;

public class PerformanceTest {

   /*static final GlobalContext CONTEXT =
            DefaultGlobalContext.builder()
                .addBinaryOperator(BinaryOperator.create("+", low(), ctx->ctx.getFirstValue() + ctx.getSecondValue()))
                .addBinaryOperator(BinaryOperator.create("-", low(), ctx->ctx.getFirstValue() - ctx.getSecondValue()))
                .addBinaryOperator(BinaryOperator.create("*", high(), ctx->ctx.getFirstValue() * ctx.getSecondValue()))
                .addBinaryOperator(BinaryOperator.create("/", high(), ctx->ctx.getFirstValue() / ctx.getSecondValue()))
                .addUnaryOperator(UnaryOperator.create("+", higherThan(high()), ctx->+ctx.getValue()))
                .addUnaryOperator(UnaryOperator.create("-", higherThan(high()), ctx->-ctx.getValue()))
                .addFunction(FunctionOperator.create("max(a,b)", ctx->Math.max(ctx.param("a"), ctx.param("b"))))
                .build();*/

    //static final ExpressionParser PARSER = new com.xplusj.interpreter.parser.ExpressionParser(CONTEXT);

    static final ExpressionContext env = ExpressionContext.builder().build();

    public static void main(String[] args) {
        System.out.println(env.expression("8/2*(2+2)").eval());
        String expression = "(a/(2+b)-max(a,b)*2)*(a/(2+b)-max(a,b)*2)*(a/(2+b)-max(a,b)*2)";
        double a = 1;
        double b = 2;
        double result = (a/(2+b)-Math.max(a,b)*2)*(a/(2+b)-Math.max(a,b)*2)*(a/(2+b)-Math.max(a,b)*2);

        VariableContext vars = VariableContext.vars(
                Variable.var("a", a),
                Variable.var("b", b)
            );

        Expression iExp = env.expression(expression);
        Expression fExp = env.formula(expression);

        double iVal = iExp.eval(vars);
        double fVal = fExp.eval(vars);
        if(result != iVal || result != fVal) {
            System.out.printf("result: %s, iVal: %s, fVal: %s", result,iVal,fVal);
            return;
        }

        int loops = 100000;
        long start = 0L;

        start = System.currentTimeMillis();
        for(int i = 0; i < loops; i++)
            iExp.eval(vars);

        long totalIExp = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for(int i = 0; i < loops; i++)
            fExp.eval(vars);

        long totalFExp = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for(int i = 0; i < loops; i++)
            env.expression(expression).eval(vars);

        long totalExp2 = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for(int i = 0; i < loops; i++)
            env.formula(expression).eval(vars);

        long totalFExp2 = System.currentTimeMillis() - start;

        System.out.println("Inline: " + totalIExp);
        System.out.println("Inline 2: " + totalExp2);
        System.out.println("Formula: " + totalFExp);
        System.out.println("Formula 2: " + totalFExp2);
    }
}
