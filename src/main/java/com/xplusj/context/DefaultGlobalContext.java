package com.xplusj.context;


import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.operator.*;

import java.util.HashMap;
import java.util.Map;

public class DefaultGlobalContext implements GlobalContext {

    private final Map<String, FunctionOperator> functionsMap;
    private final Map<Character, BinaryOperator> binaryOperatorsMap;
    private final Map<Character, UnaryOperator> unaryOperatorsMap;
    private final VariableContext constantsContext;

    private DefaultGlobalContext(Map<String, FunctionOperator> functionsMap,
                                Map<Character, BinaryOperator> binaryOperatorsMap,
                                Map<Character, UnaryOperator> unaryOperatorsMap,
                                VariableContext constantsContext) {
        this.functionsMap = functionsMap;
        this.binaryOperatorsMap = binaryOperatorsMap;
        this.unaryOperatorsMap = unaryOperatorsMap;
        this.constantsContext = constantsContext;
    }

    @Override
    public boolean hasFunction(String name) {
        return functionsMap.containsKey(name);
    }

    @Override
    public boolean hasBinaryOperator(char symbol) {
        return binaryOperatorsMap.containsKey(symbol);
    }

    @Override
    public boolean hasUnaryOperator(char symbol) {
        return unaryOperatorsMap.containsKey(symbol);
    }

    @Override
    public boolean hasConstant(String name) {
        return constantsContext.contains(name);
    }

    @Override
    public BinaryOperator getBinaryOperator(char symbol) {
        return binaryOperatorsMap.get(symbol);
    }

    @Override
    public UnaryOperator getUnaryOperator(char symbol) {
        return unaryOperatorsMap.get(symbol);
    }

    @Override
    public FunctionOperator getFunction(String name) {
        return functionsMap.get(name);
    }

    @Override
    public double getConstant(String name) {
        return constantsContext.value(name);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements GlobalContext.Builder{

        private Map<String, FunctionOperator> functionsMap = new HashMap<>();
        private Map<Character, BinaryOperator> binaryOperatorsMap = new HashMap<>();
        private Map<Character, UnaryOperator> unaryOperatorsMap = new HashMap<>();
        private VariableContext.Builder constantsContextBuilder = DefaultVariableContext.builder();

        @Override
        public GlobalContext.Builder addUnaryOperator(UnaryOperator...operator) {
            for(UnaryOperator op : operator)
                unaryOperatorsMap.put(op.getSymbol(), op);

            return this;
        }

        @Override
        public GlobalContext.Builder addBinaryOperator(BinaryOperator...operator) {
            for(BinaryOperator op : operator)
                binaryOperatorsMap.put(op.getSymbol(), op);

            return this;
        }

        @Override
        public GlobalContext.Builder addFunction(FunctionOperator...function) {
            for(FunctionOperator func : function)
                functionsMap.put(func.getName(),func);

            return this;
        }

        @Override
        public GlobalContext.Builder addConstant(String name, double value) {
            constantsContextBuilder.add(name,value);
            return this;
        }

        @Override
        public GlobalContext build() {
            return new DefaultGlobalContext(
                    functionsMap,
                    binaryOperatorsMap,
                    unaryOperatorsMap,
                    constantsContextBuilder.build()
            );
        }
    }
}
