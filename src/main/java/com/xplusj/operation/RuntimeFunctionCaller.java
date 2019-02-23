package com.xplusj.operation;

import com.xplusj.Environment;
import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.function.FunctionRuntimeContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RuntimeFunctionCaller implements FunctionCaller {

    private final ExpressionFunction function;
    private final Environment env;

    @Override
    public double call(double... params) {
        //TODO improve algorithm and exception

        if(params.length != function.getParams().size()){
            throw new IllegalArgumentException("params not compatible");
        }

        return function.getFunction().apply(
                new FunctionRuntimeContext(params, function.getParams(), env));
    }
}
