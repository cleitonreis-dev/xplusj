package com.xplusj.core;

public interface VariableContainer {
    double value(String name);
    boolean contains(String name);

    interface Builder{
        Builder add(String name, double value);
        VariableContainer build();
    }
}
