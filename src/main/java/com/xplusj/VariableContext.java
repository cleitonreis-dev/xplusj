package com.xplusj;

public interface VariableContext {
    double value(String name);
    boolean contains(String name);

    interface Builder{
        Builder add(String name, double value);
        VariableContext build();
    }

    VariableContext EMPTY = new VariableContext(){
        @Override
        public double value(String name) {
            return 0;
        }

        @Override
        public boolean contains(String name) {
            return false;
        }
    };
}
