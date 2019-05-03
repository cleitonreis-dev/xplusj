package com.xplusj.interpreter.operator;

import com.xplusj.operator.FunctionOperatorContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.lang.String.format;

@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "params"})
public class FunctionOperator implements com.xplusj.operator.FunctionOperator {
    private static final Precedence PRECEDENCE = Precedence.lowerThan(Precedence.highest());

    private final String name;
    private final Map<String,Integer> params;
    private final Function<com.xplusj.operator.FunctionOperatorContext, Double> function;

    public FunctionOperator(String name, Set<String> params, Function<com.xplusj.operator.FunctionOperatorContext, Double> function) {
        this.name = name;
        this.function = function;
        this.params = new HashMap<>();

        int i = 0;
        for(String param : params){
            this.params.put(param,i++);
        }
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int getParamsLength() {
        return params.size();
    }

    @Override
    public OperatorType getType() {
        return OperatorType.FUNCTION;
    }

    @Override
    public Precedence getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public boolean precedes(Operator<?> executor) {
        return PRECEDENCE.compareTo(executor.getPrecedence()) > 0;
    }

    @Override
    public double execute(FunctionOperatorContext context) {
        return function.apply(context);
    }

    @Override
    public int paramIndex(String name) {
        if(!params.containsKey(name))
            throw new IllegalArgumentException(format("Param %s not found for function %s. Valid params are: %s",name, this.name, this.params.keySet()));

        return params.get(name);
    }
}
