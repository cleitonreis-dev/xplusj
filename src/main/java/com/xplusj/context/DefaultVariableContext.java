package com.xplusj.context;

import com.xplusj.VariableContext;
import com.xplusj.variable.Variable;

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

    public static VariableContext.Builder builder() {
        return new Builder();
    }

    public static class Builder implements VariableContext.Builder{

        private Map<String,Double> vars = new HashMap<>();

        @Override
        public VariableContext.Builder add(Variable...vars) {
            for(Variable var : vars)
                this.vars.put(var.getName(),var.getValue());

            return this;
        }

        @Override
        public VariableContext build() {
            return new DefaultVariableContext(vars);
        }
    }
}
