package com.xplusj.operator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public interface FunctionOperator extends Operator<FunctionOperatorContext> {
    String getName();

    int paramIndex(String name);

    //TODO improve exception handling put the creation logic in another class
    static FunctionOperator create(String name, Function<FunctionOperatorContext, Double> function) {
        int openParenthesisIndex = name.indexOf('(');
        if (openParenthesisIndex <= 0)
            throw new IllegalArgumentException("incorrect function name");

        int closeParenthesisIndex = name.indexOf(')');
        if (closeParenthesisIndex != name.length() - 1)
            throw new IllegalArgumentException("incorrect function name");

        int paramDelimiterIndex = name.indexOf(',');
        if (paramDelimiterIndex == 0 || (paramDelimiterIndex > 0
                && !(openParenthesisIndex < paramDelimiterIndex
                && paramDelimiterIndex < closeParenthesisIndex)))
            throw new IllegalArgumentException("incorrect function name");

        String fName = name.substring(0, openParenthesisIndex);
        Set<String> params = Collections.emptySet();

        if (paramDelimiterIndex > 0){
            String[] paramNames = name.substring(openParenthesisIndex + 1, closeParenthesisIndex).split(",");
            params = new HashSet<>(Arrays.asList(paramNames));

            if(paramNames.length != params.size())
                throw new IllegalArgumentException("incorrect function name");
        }

        return new com.xplusj.interpreter.operator.FunctionOperator(fName, params,function);
    }
}
