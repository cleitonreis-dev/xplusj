package com.xplusj;

import com.xplusj.operation.BuiltinOperations;
import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

class DefaultEnvironment implements Environment {

    private final Map<String, ExpressionFunction> functionsMap;
    private final Map<Character, BinaryOperator> binaryOperatorsMap;
    private final Map<Character, UnaryOperator> unaryOperatorsMap;
    private final Map<String, Double> constantsMap;

    private DefaultEnvironment(List<ExpressionFunction> functions,
                              List<BinaryOperator> binaryOperators,
                              List<UnaryOperator> unaryOperators,
                               Map<String, Double> constantsMap) {

        Map<String, ExpressionFunction> functionsMap = new HashMap<>(functions.size());
        functions.forEach(f->functionsMap.put(f.getName(),f));
        this.functionsMap = unmodifiableMap(functionsMap);

        Map<Character, BinaryOperator> binaryOperatorsMap = new HashMap<>(binaryOperators.size());
        binaryOperators.forEach(bo->binaryOperatorsMap.put(bo.getSymbol(),bo));
        this.binaryOperatorsMap = unmodifiableMap(binaryOperatorsMap);

        Map<Character, UnaryOperator> unaryOperatorsMap = new HashMap<>(unaryOperators.size());
        unaryOperators.forEach(uo->unaryOperatorsMap.put(uo.getSymbol(), uo));
        this.unaryOperatorsMap = unmodifiableMap(unaryOperatorsMap);

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

    static DefaultEnvironment.Builder builder(){
        return new Builder();
    }

    //TODO rethink a better builder interface
    public static class Builder{
        private List<ExpressionFunction> functions = new ArrayList<>(BuiltinOperations.functions());
        private List<BinaryOperator> binaryOperators = new ArrayList<>(BuiltinOperations.binaryOperators());
        private List<UnaryOperator> unaryOperators = new ArrayList<>(BuiltinOperations.unaryOperators());
        private Map<String,Double> constants = BuiltinOperations.constants();

        private Builder() {}

        public Builder functions(List<ExpressionFunction> functions){
            this.functions.addAll(functions);
            return this;
        }

        public Builder binaryOperators(List<BinaryOperator> operators){
            this.binaryOperators.addAll(operators);
            return this;
        }

        public Builder unaryOperators(List<UnaryOperator> operators){
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
