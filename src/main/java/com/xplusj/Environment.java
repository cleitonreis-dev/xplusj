package com.xplusj;

import com.xplusj.context.DefaultEnvironment;
import com.xplusj.operator.Operators;

public interface Environment {

    Expression expression(String expression);

    Expression formula(String formula);

    Environment appendContext(GlobalContext context);

    static Environment env(){
        return DefaultEnvironment.create(GlobalContext.builder()
                .addBinaryOperator(Operators.Binaries.OPERATORS)
                .addUnaryOperator(Operators.Unaries.OPERATORS)
                .addFunction(Operators.Functions.FUNCTIONS)
                .build());
    }
}
