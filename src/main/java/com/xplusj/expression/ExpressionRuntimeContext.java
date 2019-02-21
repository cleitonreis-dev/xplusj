package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.operation.FunctionCaller;
import com.xplusj.operation.RuntimeContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ExpressionRuntimeContext implements RuntimeContext {
    private final Environment env;

    @Override
    public FunctionCaller getFunction(String name) {
        return new RuntimeFunctionCaller(env.getFunction(name), env);
    }

    @Override
    public Double getConstant(String name) {
        return env.getConstant(name);
    }
}
