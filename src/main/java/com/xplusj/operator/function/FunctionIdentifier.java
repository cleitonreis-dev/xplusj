package com.xplusj.operator.function;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@EqualsAndHashCode(of = {"name", "paramsLength"})
@ToString(of = {"name", "params"})
public class FunctionIdentifier {
    private static final String VAR_ARGS_IDENTIFIER = "...";

    private final String name;
    private final int paramsLength;
    private final List<FunctionParam> params;
    private final Map<String,Integer> paramIndex;

    protected FunctionIdentifier(String name, List<FunctionParam> params) {
        this.name = name;
        this.params = params;
        this.paramsLength = params.size();
        this.paramIndex = new HashMap<>();

        params.forEach(param->this.paramIndex.put(param.getName(),param.getIndex()));
    }

    public String getName() {
        return name;
    }

    public List<FunctionParam> getParams() {
        return params;
    }

    public int getParamsLength() {
        return paramsLength;
    }

    public int getParamIndex(String name){
        if(!this.paramIndex.containsKey(name))
            throw new IllegalArgumentException(
                format("Param %s not found for function %s. Valid params are: %s",
                    name, this.name, this.params.stream().map(FunctionParam::getName).collect(Collectors.joining(","))
                )
            );

        return this.paramIndex.get(name);
    }

    public static FunctionIdentifier create(String name){
        int openParenthesisIndex = name.indexOf('(');
        if (openParenthesisIndex <= 0)
            throw new IllegalArgumentException(format("Function identifier '%s' does not have '('", name));

        int closeParenthesisIndex = name.indexOf(')');
        if (closeParenthesisIndex != name.length() - 1)
            throw new IllegalArgumentException(format("Function identifier '%s' does not have ')'", name));

        List<FunctionParam> params = new ArrayList<>();
        String paramsStr = name.substring(openParenthesisIndex + 1, closeParenthesisIndex).trim();

        if(paramsStr.isEmpty())
            throw new IllegalArgumentException(format("Function identifier '%s' must have at least one parameter", name));

        String[] paramNames = name.substring(openParenthesisIndex + 1, closeParenthesisIndex).split(",");
        boolean varArgsFound = false;

        for(int i = 0; i < paramNames.length; i++) {
            if(varArgsFound)
                throw new IllegalArgumentException(format("Function identifier '%s', 'var-args' must be the last parameter", name));

            boolean isVarArgs = paramNames[i].equals(VAR_ARGS_IDENTIFIER);
            if(isVarArgs)
                varArgsFound = true;

            params.add(FunctionParam.create(i, paramNames[i].trim(), isVarArgs));
        }

        return new FunctionIdentifier(
            name.substring(0, openParenthesisIndex),
            Collections.unmodifiableList(params)
        );
    }
}
