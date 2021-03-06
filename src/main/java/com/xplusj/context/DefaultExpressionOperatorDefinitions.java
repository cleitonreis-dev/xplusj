package com.xplusj.context;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import java.util.*;

public class DefaultExpressionOperatorDefinitions implements ExpressionOperatorDefinitions {

    private final Map<String, UnaryOperatorDefinition> unaryOperators;
    private final Map<String, BinaryOperatorDefinition> binaryOperators;
    private final Map<String, FunctionOperatorDefinition> functionOperators;
    private final Map<String, Constant> constants;

    private DefaultExpressionOperatorDefinitions(Map<String, UnaryOperatorDefinition> unaryOperators,
                                                 Map<String, BinaryOperatorDefinition> binaryOperators,
                                                 Map<String, FunctionOperatorDefinition> functionOperators,
                                                 Map<String, Constant> constants) {
        this.unaryOperators = unaryOperators;
        this.binaryOperators = binaryOperators;
        this.functionOperators = functionOperators;
        this.constants = constants;
    }

    @Override
    public boolean hasFunction(String name) {
        return functionOperators.containsKey(name);
    }

    @Override
    public boolean hasBinaryOperator(String symbol) {
        return binaryOperators.containsKey(symbol);
    }

    @Override
    public boolean hasUnaryOperator(String symbol) {
        return unaryOperators.containsKey(symbol);
    }

    @Override
    public boolean hasConstant(String name) {
        return constants.containsKey(name);
    }

    @Override
    public BinaryOperatorDefinition getBinaryOperator(String symbol) {
        return binaryOperators.get(symbol);
    }

    @Override
    public UnaryOperatorDefinition getUnaryOperator(String symbol) {
        return unaryOperators.get(symbol);
    }

    @Override
    public FunctionOperatorDefinition getFunction(String name) {
        return functionOperators.get(name);
    }

    @Override
    public Constant getConstant(String name) {
        return constants.get(name);
    }

    @Override
    public ExpressionOperatorDefinitions append(ExpressionOperatorDefinitions newDefinitions) {
        return DefaultExpressionOperatorDefinitionsAppender.append(this, newDefinitions);
    }

    public static ExpressionOperatorDefinitions.Builder builder(){
        return new Builder();
    }

    static class Builder implements ExpressionOperatorDefinitions.Builder{

        private List<UnaryOperatorDefinition> unaries = new ArrayList<>();
        private List<BinaryOperatorDefinition> binaries = new ArrayList<>();
        private List<FunctionOperatorDefinition> functions = new ArrayList<>();
        private List<Constant> constants = new ArrayList<>();

        @Override
        public ExpressionOperatorDefinitions.Builder addUnaryOperator(UnaryOperatorDefinition... operator) {
            this.unaries.addAll(Arrays.asList(operator));
            return this;
        }

        @Override
        public ExpressionOperatorDefinitions.Builder addBinaryOperator(BinaryOperatorDefinition... operator) {
            this.binaries.addAll(Arrays.asList(operator));
            return this;
        }

        @Override
        public ExpressionOperatorDefinitions.Builder addFunction(FunctionOperatorDefinition... function) {
            this.functions.addAll(Arrays.asList(function));
            return this;
        }

        @Override
        public ExpressionOperatorDefinitions.Builder addConstant(Constant...constants) {
            this.constants.addAll(Arrays.asList(constants));
            return this;
        }

        @Override
        public ExpressionOperatorDefinitions build() {
            Map<String, UnaryOperatorDefinition> unaryMap = new HashMap<>();
            Map<String, BinaryOperatorDefinition> binariesMap = new HashMap<>();
            Map<String, FunctionOperatorDefinition> functionsMap = new HashMap<>();
            Map<String, Constant> constantsMap = new HashMap<>();

            unaries.forEach(operator->unaryMap.put(operator.getIdentifier(), operator));
            binaries.forEach(operator->binariesMap.put(operator.getIdentifier(), operator));
            functions.forEach(operator->functionsMap.put(operator.getIdentifier(), operator));
            constants.forEach(constant->constantsMap.put(constant.getName(), constant));

            return new DefaultExpressionOperatorDefinitions(unaryMap,binariesMap,functionsMap,constantsMap);
        }
    }
}
