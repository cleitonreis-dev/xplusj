package com.xplusj;

import com.xplusj.context.DefaultEnvironment;

public interface Environment {

    Expression expression(String expression);

    Expression formula(String formula);

    Environment appendContext(GlobalContext context);

    static Environment env(){
        return DefaultEnvironment.create(GlobalContext.builder()

                .build());
    }
}
