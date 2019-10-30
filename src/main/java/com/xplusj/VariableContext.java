package com.xplusj;

import com.xplusj.context.DefaultVariableContext;

public interface VariableContext {
    VariableContext EMPTY = builder().build();

    double value(String name);
    boolean contains(String name);

    static Builder builder(){
        return new DefaultVariableContext.Builder();
    }

    interface Builder{
        Builder add(String name, double value);
        VariableContext build();
    }
}
