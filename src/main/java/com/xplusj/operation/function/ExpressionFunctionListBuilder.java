package com.xplusj.operation.function;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpressionFunctionListBuilder {
    private List<ExpressionFunction> functions = new ArrayList<>();

    public ExpressionFunctionListBuilder add(ExpressionFunction function){
        functions.add(function);
        return this;
    }

    public ExpressionFunctionListBuilder add(String nameAndParams, Function<FunctionRuntimeContext,Double> function){
        return add(ExpressionFunctionFactory.create(nameAndParams, function));
    }

    public List<ExpressionFunction> build(){
        return this.functions;
    }

    public static ExpressionFunctionListBuilder builder(){
        return new ExpressionFunctionListBuilder();
    }
}
