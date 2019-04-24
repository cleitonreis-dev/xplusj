package com.xplusj.operation.function;

import com.xplusj.operation.Operation;
import com.xplusj.operation.OperationType;
import com.xplusj.operation.OperationVisitor;
import com.xplusj.operation.Precedence;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "name")
@ToString(of = {"name", "params"})
public class ExpressionFunction implements Operation<FunctionRuntimeContext> {
    private static final Precedence PRECEDENCE = Precedence.lowerThan(Precedence.highest());

    private final String name;
    private final Map<String,Integer> params;
    private final Function<FunctionRuntimeContext, Double> function;

    Map<String,Integer> getParams(){
        return params;
    }

    public String getName(){
        return name;
    }

    public int getParamsLength() {
        return params.size();
    }

    @Override
    public OperationType geType() {
        return OperationType.FUNCTION;
    }

    @Override
    public Precedence getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public boolean precedes(Operation<?> executor) {
        return PRECEDENCE.compareTo(executor.getPrecedence()) > 0;
    }

    @Override
    public double accept(OperationVisitor visitor) {
        return visitor.execute(this);
    }

    @Override
    public double execute(FunctionRuntimeContext context) {
        return function.apply(context);
    }

    public static ExpressionFunction function(String nameAndParams, Function<FunctionRuntimeContext, Double> function){
        return ExpressionFunctionFactory.create(nameAndParams,function);
    }

    public static ExpressionFunctionListBuilder listBuilder(){
        return ExpressionFunctionListBuilder.builder();
    }

}
