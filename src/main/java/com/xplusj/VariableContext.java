package com.xplusj;

public interface VariableContext {
    double value(String name);
    boolean contains(String name);

    interface Builder{
        Builder add(String name, double value);
        VariableContext build();
    }
}
