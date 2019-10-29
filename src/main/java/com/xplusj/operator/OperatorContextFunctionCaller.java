package com.xplusj.operator;

import com.xplusj.Environment;

import static java.lang.String.format;

public interface OperatorContextFunctionCaller {

    double call(Environment env, String functionName, double... params);

    OperatorContextFunctionCaller DEFAULT = (env, name, params)->{
        FunctionOperator function = env.getContext().getFunction(name);

        if(function == null)
            throw new IllegalArgumentException(format("Function '%s' not found", name));

        int paramsLength = function.getParamsLength();
        if(params.length != paramsLength)
            throw new IllegalArgumentException(
                format("Wrong number of parameters given to function '%s': expected %s, but current is %s",
                        name, paramsLength, params.length)
            );

        return function.execute(new FunctionOperatorContext(function,env,params));
    };
}
