package com.xplusj.operation.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.Collections.unmodifiableMap;

public class ExpressionFunctionFactory {
    public static ExpressionFunction create(String nameAndParams, Function<FunctionRuntimeContext, Double> function){
        int openingParenthesis = nameAndParams.indexOf('(');
        int closingParenthesis = nameAndParams.indexOf(')');

        //TODO validate name and parenthesis

        String name = nameAndParams.substring(0, openingParenthesis);

        String[] paramNames = nameAndParams.substring(openingParenthesis + 1, closingParenthesis).split(",");
        Map<String,Integer> params = new HashMap<>(paramNames.length);

        for(int i = 0; i < paramNames.length; i++)
            params.put(paramNames[i], i);

        return new ExpressionFunction(name, unmodifiableMap(params),function);
    }
}
