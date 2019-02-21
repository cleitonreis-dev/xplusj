package com.xplusj.operation;

import com.xplusj.Environment;
import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.function.FunctionRuntimeContext;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class RuntimeFunctionCaller implements FunctionCaller {

    private final ExpressionFunction function;
    private final Environment env;

    @Override
    public double call(double... params) {
        //TODO improve algorithm and exception

        String[] functionParams = function.getParams();
        if(params.length != functionParams.length){
            throw new IllegalArgumentException("params not compatible");
        }

        Map<String,Double> paramsMap = new HashMap<>(params.length);
        for(int i = 0; i < functionParams.length; i++)
            paramsMap.put(functionParams[i], params[i]);

        return function.getFunction().apply(new FunctionRuntimeContext(paramsMap, env));
    }
}
