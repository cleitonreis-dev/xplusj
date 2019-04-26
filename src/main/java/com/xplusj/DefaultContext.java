package com.xplusj;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

class DefaultContext implements Environment {

    private final Map<String, ExpressionFunction> functionsMap;
    private final Map<Character, BinaryOperator> binaryOperatorsMap;
    private final Map<Character, UnaryOperator> unaryOperatorsMap;
    private final Map<String, Double> constantsMap;

    public DefaultContext(List<ExpressionFunction> functions,
                           List<BinaryOperator> binaryOperators,
                           List<UnaryOperator> unaryOperators,
                           Map<String, Double> constantsMap) {
        this.functionsMap = unmodifiableMap(functions.stream().collect(toMap(ExpressionFunction::getName, f->f)));
        this.binaryOperatorsMap = unmodifiableMap(binaryOperators.stream().collect(toMap(BinaryOperator::getSymbol,o->o)));
        this.unaryOperatorsMap = unmodifiableMap(unaryOperators.stream().collect(toMap(UnaryOperator::getSymbol,u->u)));
        this.constantsMap = unmodifiableMap(new HashMap<>(constantsMap));
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
        return constantsMap.containsKey(name);
    }

    @Override
    public Operator<BinaryOperatorRuntimeContext> getBinaryOperator(char symbol) {
        return binaryOperatorsMap.get(symbol);
    }

    @Override
    public Operator<UnaryOperatorRuntimeContext> getUnaryOperator(char symbol) {
        return unaryOperatorsMap.get(symbol);
    }

    @Override
    public ExpressionFunction getFunction(String name) {
        return functionsMap.get(name);
    }

    @Override
    public Double getConstant(String name) {
        return constantsMap.get(name);
    }
}
