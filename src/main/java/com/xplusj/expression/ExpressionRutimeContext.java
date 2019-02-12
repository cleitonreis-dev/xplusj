package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.FunctionCaller;
import com.xplusj.RuntimeContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ExpressionRutimeContext implements RuntimeContext {
    private final Environment env;

    @Override
    public FunctionCaller getFunction(String name) {
        return null;
    }
}
