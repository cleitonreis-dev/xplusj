package com.xplusj.context;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;

import java.util.*;

public class DefaultExpressionOperatorDefinitions implements ExpressionOperatorDefinitions {

    private final Map<String, UnaryOperator> unaryOperators;
    private final Map<String, BinaryOperator> binaryOperators;
    private final Map<String, FunctionOperator> functionOperators;
    private final Map<String, Constant> constants;

    private DefaultExpressionOperatorDefinitions(Map<String, UnaryOperator> unaryOperators,
                                                 Map<String, BinaryOperator> binaryOperators,
                                                 Map<String, FunctionOperator> functionOperators,
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
    public BinaryOperator getBinaryOperator(String symbol) {
        return binaryOperators.get(symbol);
    }

    @Override
    public UnaryOperator getUnaryOperator(String symbol) {
        return unaryOperators.get(symbol);
    }

    @Override
    public FunctionOperator getFunction(String name) {
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

        private List<UnaryOperator> unaries = new ArrayList<>();
        private List<BinaryOperator> binaries = new ArrayList<>();
        private List<FunctionOperator> functions = new ArrayList<>();
        private List<Constant> constants = new ArrayList<>();

        @Override
        public ExpressionOperatorDefinitions.Builder addUnaryOperator(UnaryOperator... operator) {
            this.unaries.addAll(Arrays.asList(operator));
            return this;
        }

        @Override
        public ExpressionOperatorDefinitions.Builder addBinaryOperator(BinaryOperator... operator) {
            this.binaries.addAll(Arrays.asList(operator));
            return this;
        }

        @Override
        public ExpressionOperatorDefinitions.Builder addFunction(FunctionOperator... function) {
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
            Map<String, UnaryOperator> unaryMap = new HashMap<>();
            Map<String, BinaryOperator> binariesMap = new HashMap<>();
            Map<String, FunctionOperator> functionsMap = new HashMap<>();
            Map<String, Constant> constantsMap = new HashMap<>();

            unaries.forEach(operator->unaryMap.put(operator.getIdentifier(), operator));
            binaries.forEach(operator->binariesMap.put(operator.getIdentifier(), operator));
            functions.forEach(operator->functionsMap.put(operator.getIdentifier(), operator));
            constants.forEach(constant->constantsMap.put(constant.getName(), constant));

            return new DefaultExpressionOperatorDefinitions(unaryMap,binariesMap,functionsMap,constantsMap);
        }
    }
}
