package com.xplusj.operation;

import com.xplusj.Environment;
import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.function.FunctionRuntimeContext;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public abstract class RuntimeContext{
    private final Environment env;

    public double call(String name, double...values){
        ExpressionFunction function = env.getFunction(name);
        if(function == null)
            throw new IllegalArgumentException(format("Function '%s' not found", name));

        int functionParamsSize = function.getParams().size();
        if(values.length != functionParamsSize)
            throw new IllegalArgumentException(
                format("Wrong number of parameters given to function '%s': expected %s, but current is %s",
                        name, functionParamsSize, values.length)
            );

        return function.getFunction().apply(new FunctionRuntimeContext(values, function.getParams(), env));
    }

    public Double getConstant(String name){
        return env.getConstant(name);
    }
}
