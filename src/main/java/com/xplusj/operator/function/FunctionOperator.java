package com.xplusj.operator.function;

import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorType;
import com.xplusj.operator.Precedence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

public class FunctionOperator extends Operator<FunctionOperatorContext> {
    private static final Precedence PRECEDENCE = Precedence.lowerThan(Precedence.highest());

    private final boolean varArgs;
    private final List<String> params;
    private final Map<String,Integer> paramIndex;

    protected FunctionOperator(String name, List<String> params, boolean varArgs, Function<FunctionOperatorContext, Double> function) {
        super(name, OperatorType.FUNCTION,PRECEDENCE,function,params.size());
        this.varArgs = varArgs;
        this.params = params;

        this.paramIndex = new HashMap<>();
        for(int i = 0; i < params.size(); i++)
            this.paramIndex.put(params.get(i), i);
    }

    public int paramIndex(String name) {
        if(!this.paramIndex.containsKey(name))
            throw new IllegalArgumentException(
                    format("Param %s not found for function %s. Valid params are: %s",
                            name, getIdentifier(), String.join(",",this.params))
                    );

        return paramIndex.get(name);
    }

    public List<String> getParams(){
        return params;
    }

    public boolean isVarArgs(){
        return this.varArgs;
    }

    @Override
    public String toString() {
        return "FunctionOperator{" +
                "varArgs=" + varArgs +
                ", params=" + params +
                ", paramIndex=" + paramIndex +
                ", super=" + super.toString() +
                '}';
    }

    public static FunctionOperator func(String name, Function<FunctionOperatorContext, Double> function) {
        FunctionIdentifier identifier = FunctionIdentifier.create(name);
        return new FunctionOperator(identifier.name,identifier.params,identifier.isVarargs,function);
    }

    public static FunctionOperator func(String name, String function) {
        FunctionIdentifier identifier = FunctionIdentifier.create(name);
        return new FunctionOperator(identifier.name,identifier.params,identifier.isVarargs,new CompiledFunction(function));
    }
}
