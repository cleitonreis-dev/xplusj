package com.xplusj.context;


import com.xplusj.core.GlobalContext;
import com.xplusj.core.VariableContainer;
import com.xplusj.core.operator.*;

import java.util.Map;

public class DefaultGlobalContext implements GlobalContext {

    private final Map<String, FunctionOperator> functionsMap;
    private final Map<Character, BinaryOperator> binaryOperatorsMap;
    private final Map<Character, UnaryOperator> unaryOperatorsMap;
    private final VariableContainer constantsContainer;

    public DefaultGlobalContext(Map<String, FunctionOperator> functionsMap,
                                Map<Character, BinaryOperator> binaryOperatorsMap,
                                Map<Character, UnaryOperator> unaryOperatorsMap,
                                VariableContainer constantsContainer) {
        this.functionsMap = functionsMap;
        this.binaryOperatorsMap = binaryOperatorsMap;
        this.unaryOperatorsMap = unaryOperatorsMap;
        this.constantsContainer = constantsContainer;
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
        return constantsContainer.contains(name);
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
        return constantsContainer.value(name);
    }
}
