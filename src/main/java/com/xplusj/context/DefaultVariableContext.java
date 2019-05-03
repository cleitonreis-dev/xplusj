package com.xplusj.context;

import com.xplusj.VariableContext;

import java.util.HashMap;
import java.util.Map;

public class DefaultVariableContext implements VariableContext {

    private final Map<String,Double> vars;

    private DefaultVariableContext(Map<String, Double> vars) {
        this.vars = vars;
    }

    @Override
    public double value(String name) {
        return vars.get(name);
    }

    @Override
    public boolean contains(String name) {
        return vars.containsKey(name);
    }

    public static class Builder implements VariableContext.Builder{

        private Map<String,Double> vars = new HashMap<>();

        @Override
        public VariableContext.Builder add(String name, double value) {
            vars.put(name,value);
            return this;
        }

        @Override
        public VariableContext build() {
            return new DefaultVariableContext(vars);
        }
    }
}
