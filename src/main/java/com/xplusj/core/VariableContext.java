package com.xplusj.core;

public interface VariableContext {
    double value(String name);
    boolean contains(String name);

    interface Builder{
        Builder add(String name, double value);
        VariableContext build();
    }
}
