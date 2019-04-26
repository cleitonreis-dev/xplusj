package com.xplusj;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.*;

import java.util.Map;

import static com.xplusj.operation.BuiltinOperations.*;
import static java.util.stream.Collectors.toMap;

public class RootContext implements Environment{

    private static final RootContext INSTANCE = new RootContext(
        functions().stream().collect(toMap(ExpressionFunction::getName,f->f)),
        binaryOperators().stream().collect(toMap(BinaryOperator::getSymbol,o->o)),
        unaryOperators().stream().collect(toMap(UnaryOperator::getSymbol,u->u)),
        constants()
    );

    private final Map<String, ExpressionFunction> functionsMap;
    private final Map<Character, BinaryOperator> binaryOperatorsMap;
    private final Map<Character, UnaryOperator> unaryOperatorsMap;
    private final Map<String, Double> constantsMap;

    public RootContext(Map<String, ExpressionFunction> functionsMap,
                       Map<Character, BinaryOperator> binaryOperatorsMap,
                       Map<Character, UnaryOperator> unaryOperatorsMap,
                       Map<String, Double> constantsMap) {
        this.functionsMap = functionsMap;
        this.binaryOperatorsMap = binaryOperatorsMap;
        this.unaryOperatorsMap = unaryOperatorsMap;
        this.constantsMap = constantsMap;
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

    public static Environment instance(){
        return INSTANCE;
    }
}
