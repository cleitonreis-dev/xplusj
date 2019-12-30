package com.xplusj.operator.function;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;


class FunctionIdentifier {
    private static final String VAR_ARGS_IDENTIFIER = "...";

    final String name;
    final List<String> params;
    final boolean isVarargs;

    private FunctionIdentifier(String name, List<String> params, boolean isVarargs) {
        this.name = name;
        this.params = params;
        this.isVarargs = isVarargs;
    }

    public static FunctionIdentifier create(String name){
        int openParenthesisIndex = name.indexOf('(');
        if (openParenthesisIndex <= 0)
            throw new IllegalArgumentException(format("Function identifier '%s' does not have '('", name));

        int closeParenthesisIndex = name.indexOf(')');
        if (closeParenthesisIndex != name.length() - 1)
            throw new IllegalArgumentException(format("Function identifier '%s' does not have ')'", name));

        List<String> params = new ArrayList<>();
        String paramsStr = name.substring(openParenthesisIndex + 1, closeParenthesisIndex).trim();

        if(paramsStr.isEmpty())
            throw new IllegalArgumentException(format("Function identifier '%s' must have at least one parameter", name));

        String[] paramNames = name.substring(openParenthesisIndex + 1, closeParenthesisIndex).split(",");
        boolean varArgsFound = false;

        for (String paramName : paramNames) {
            if (varArgsFound)
                throw new IllegalArgumentException(format("Function identifier '%s', 'varargs' must be the last parameter", name));

            boolean isVarArgs = paramName.equals(VAR_ARGS_IDENTIFIER);
            if (isVarArgs)
                varArgsFound = true;

            params.add(paramName.trim());
        }

        return new FunctionIdentifier(name.substring(0, openParenthesisIndex), params, varArgsFound);
    }
}
