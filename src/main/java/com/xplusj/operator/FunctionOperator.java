package com.xplusj.operator;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.function.Function;

import static java.lang.String.format;

@EqualsAndHashCode(of = "name", callSuper = true)
@ToString(of = {"name", "params"}, callSuper = true)
public class FunctionOperator extends Operator<FunctionOperatorContext>{
    private static final Precedence PRECEDENCE = Precedence.lowerThan(Precedence.highest());

    private final String name;
    private final Map<String,Integer> params;

    public FunctionOperator(String name, Set<String> params, Function<com.xplusj.operator.FunctionOperatorContext, Double> function) {
        super(OperatorType.FUNCTION,PRECEDENCE,function);
        this.name = name;
        this.params = new HashMap<>();

        int i = 0;
        for(String param : params){
            this.params.put(param,i++);
        }
    }

    public String getName(){
        return name;
    }

    public int getParamsLength() {
        return params.size();
    }

    @Override
    public double execute(FunctionOperatorContext context) {
        return function.apply(context);
    }

    public int paramIndex(String name) {
        if(!params.containsKey(name))
            throw new IllegalArgumentException(format("Param %s not found for function %s. Valid params are: %s",name, this.name, this.params.keySet()));

        return params.get(name);
    }

    //TODO improve exception handling put the creation logic in another class
    public static FunctionOperator create(String name, Function<FunctionOperatorContext, Double> function) {
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

        return new FunctionOperator(fName, params,function);
    }
}
