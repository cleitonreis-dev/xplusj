package com.xplusj.context;


import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import java.util.HashMap;
import java.util.Map;

public class DefaultGlobalContext implements GlobalContext {

    private final Map<String, FunctionOperatorDefinition> functionsMap;
    private final Map<Character, BinaryOperatorDefinition> binaryOperatorsMap;
    private final Map<Character, UnaryOperatorDefinition> unaryOperatorsMap;
    private final VariableContext constantsContext;

    private DefaultGlobalContext(Map<String, FunctionOperatorDefinition> functionsMap,
                                Map<Character, BinaryOperatorDefinition> binaryOperatorsMap,
                                Map<Character, UnaryOperatorDefinition> unaryOperatorsMap,
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
        return BinaryOperator.create(this,binaryOperatorsMap.get(symbol));
    }

    @Override
    public UnaryOperator getUnaryOperator(char symbol) {
        return UnaryOperator.create(this, unaryOperatorsMap.get(symbol));
    }

    @Override
    public FunctionOperator getFunction(String name) {
        return FunctionOperator.create(this, functionsMap.get(name));
    }

    @Override
    public double getConstant(String name) {
        return constantsContext.value(name);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder implements GlobalContext.Builder{

        private Map<String, FunctionOperatorDefinition> functionsMap = new HashMap<>();
        private Map<Character, BinaryOperatorDefinition> binaryOperatorsMap = new HashMap<>();
        private Map<Character, UnaryOperatorDefinition> unaryOperatorsMap = new HashMap<>();
        private VariableContext.Builder constantsContextBuilder = DefaultVariableContext.builder();

        @Override
        public GlobalContext.Builder addUnaryOperator(UnaryOperatorDefinition...operator) {
            for(UnaryOperatorDefinition op : operator)
                unaryOperatorsMap.put(op.getSymbol(), op);

            return this;
        }

        @Override
        public GlobalContext.Builder addBinaryOperator(BinaryOperatorDefinition...operator) {
            for(BinaryOperatorDefinition op : operator)
                binaryOperatorsMap.put(op.getSymbol(), op);

            return this;
        }

        @Override
        public GlobalContext.Builder addFunction(FunctionOperatorDefinition...function) {
            for(FunctionOperatorDefinition func : function)
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
