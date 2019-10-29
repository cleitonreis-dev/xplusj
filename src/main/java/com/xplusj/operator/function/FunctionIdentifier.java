package com.xplusj.operator.function;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

import static java.lang.String.format;

@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "paramNames"})
public class FunctionIdentifier {
    private final String name;
    private final List<String> paramNames;
    private final Map<String,Integer> paramIndex;

    public FunctionIdentifier(String name, List<String> paramNames) {
        this.name = name;
        this.paramNames = paramNames;
        this.paramIndex = new HashMap<>();

        int i = 0;
        for(String param : paramNames){
            this.paramIndex.put(param,i++);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public int getParamIndex(String name){
        if(!this.paramIndex.containsKey(name))
            throw new IllegalArgumentException(
                format("Param %s not found for function %s. Valid params are: %s",
                    name, this.name, this.paramNames
                )
            );

        return this.paramIndex.get(name);
    }

    //TODO improve exception handling and improve name evaluation
    public static FunctionIdentifier create(String name){
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

        List<String> params = Collections.emptyList();

        if (paramDelimiterIndex > 0){
            String[] paramNames = name.substring(openParenthesisIndex + 1, closeParenthesisIndex).split(",");
            params = Arrays.asList(paramNames);

            if(paramNames.length != params.size())
                throw new IllegalArgumentException("incorrect function name");
        }

        return new FunctionIdentifier(
            name.substring(0, openParenthesisIndex),
            params
        );
    }
}
