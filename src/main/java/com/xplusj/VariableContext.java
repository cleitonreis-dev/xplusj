package com.xplusj;

import com.xplusj.context.DefaultVariableContext;
import com.xplusj.variable.Variable;

public interface VariableContext {
    VariableContext EMPTY = builder().build();

    double value(String name);
    boolean contains(String name);

    static Builder builder(){
        return new DefaultVariableContext.Builder();
    }

    static VariableContext vars(Variable...vars){
        return builder().add(vars).build();
    }

    interface Builder{
        Builder add(Variable...vars);
        VariableContext build();
    }
}
