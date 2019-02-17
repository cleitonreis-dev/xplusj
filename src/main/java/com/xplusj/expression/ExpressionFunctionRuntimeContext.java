package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.function.FunctionRuntimeContext;

import java.util.Map;

public class ExpressionFunctionRuntimeContext
        extends ExpressionRutimeContext
        implements FunctionRuntimeContext {

    private final Map<String,Double> params;

    public ExpressionFunctionRuntimeContext(Map<String,Double> params, Environment env) {
        super(env);

        this.params = params;
    }

    @Override
    public double getParam(String name) {
        return params.get(name);
    }
}
