package com.xplusj;

import com.xplusj.context.DefaultEnvironment;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.operator.Operators;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface Environment {

    Expression expression(String expression);

    Expression formula(String formula);

    Environment appendContext(GlobalContext context);

    GlobalContext getContext();

    ExpressionParser getParser();

    ExpressionTokenizer getTokenizer();

    interface Builder{
        Builder setContext(GlobalContext context);
        Builder setParserFactory(ExpressionParserFactory parserFactory);
        Builder setTokenizerFactory(ExpressionTokenizerFactory tokenizerFactory);
        Builder setExpressionFactory(ExpressionFactory expressionFactory);
        Environment build();
    }

    static Builder builder(){
        return DefaultEnvironment.builder();
    }

    static Environment env(){
        GlobalContext context = GlobalContext.builder()
            .addBinaryOperator(Operators.Binaries.OPERATORS)
            .addUnaryOperator(Operators.Unaries.OPERATORS)
            .addFunction(Operators.Functions.FUNCTIONS)
            .build();

        return builder()
            .setContext(context)
            .build();
    }
}
