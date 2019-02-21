package com.xplusj.core;

import com.xplusj.BuiltinOperations;
import com.xplusj.Environment;
import com.xplusj.function.ExpressionFunction;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.UnaryOperatorRuntimeContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class DefaultEnvironment implements Environment {

    private final Map<String, ExpressionFunction> functionsMap;
    private final Map<Character, Operator<BinaryOperatorRuntimeContext>> binaryOperatorsMap;
    private final Map<Character, Operator<UnaryOperatorRuntimeContext>> unaryOperatorsMap;
    private final Map<String, Double> constantsMap;

    private DefaultEnvironment(List<ExpressionFunction> functions,
                              List<Operator<BinaryOperatorRuntimeContext>> binaryOperators,
                              List<Operator<UnaryOperatorRuntimeContext>> unaryOperators,
                               Map<String, Double> constantsMap) {

        this.functionsMap = unmodifiableMap(
                functions.stream().collect(toMap(ExpressionFunction::getName, identity())));
        this.binaryOperatorsMap = unmodifiableMap(
                binaryOperators.stream().collect(toMap(Operator::getSymbol, identity())));
        this.unaryOperatorsMap = unmodifiableMap(
                unaryOperators.stream().collect(toMap(Operator::getSymbol, identity())));
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

    public static DefaultEnvironment.Builder builder(){
        return new Builder();
    }

    //TODO rethink a better builder interface
    public static class Builder{
        private List<ExpressionFunction> functions = new ArrayList<>(BuiltinOperations.functions());
        private List<Operator<BinaryOperatorRuntimeContext>> binaryOperators = new ArrayList<>(BuiltinOperations.binaryOperators());
        private List<Operator<UnaryOperatorRuntimeContext>> unaryOperators = new ArrayList<>(BuiltinOperations.unaryOperators());
        private Map<String,Double> constants = BuiltinOperations.constants();

        private Builder() {}

        public Builder functions(List<ExpressionFunction> functions){
            this.functions.addAll(functions);
            return this;
        }

        public Builder binaryOperators(List<Operator<BinaryOperatorRuntimeContext>> operators){
            this.binaryOperators.addAll(operators);
            return this;
        }

        public Builder unaryOperators(List<Operator<UnaryOperatorRuntimeContext>> operators){
            this.unaryOperators.addAll(operators);
            return this;
        }

        public Builder constants(Map<String,Double> constants){
            this.constants.putAll(constants);
            return this;
        }

        public DefaultEnvironment build(){
            return new DefaultEnvironment(functions,binaryOperators,unaryOperators,constants);
        }
    }
}
