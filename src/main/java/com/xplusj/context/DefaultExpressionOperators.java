package com.xplusj.context;

import com.xplusj.ExpressionOperators;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.Variable;

import java.util.*;

public class DefaultExpressionOperators implements ExpressionOperators {

    private final Map<String, UnaryOperator> unaryOperators;
    private final Map<String, BinaryOperator> binaryOperators;
    private final Map<String, FunctionOperator> functionOperators;
    private final Map<String, Variable> constants;

    private DefaultExpressionOperators(Map<String, UnaryOperator> unaryOperators,
                                       Map<String, BinaryOperator> binaryOperators,
                                       Map<String, FunctionOperator> functionOperators,
                                       Map<String, Variable> constants) {
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
    public Variable getConstant(String name) {
        return constants.get(name);
    }

    @Override
    public ExpressionOperators append(ExpressionOperators newDefinitions) {
        return DefaultExpressionOperatorsAppender.append(this, newDefinitions);
    }

    public static ExpressionOperators.Builder builder(){
        return new Builder();
    }

    static class Builder implements ExpressionOperators.Builder{

        private List<UnaryOperator> unaries = new ArrayList<>();
        private List<BinaryOperator> binaries = new ArrayList<>();
        private List<FunctionOperator> functions = new ArrayList<>();
        private List<Variable> constants = new ArrayList<>();

        @Override
        public ExpressionOperators.Builder addUnaryOperator(UnaryOperator... operator) {
            this.unaries.addAll(Arrays.asList(operator));
            return this;
        }

        @Override
        public ExpressionOperators.Builder addBinaryOperator(BinaryOperator... operator) {
            this.binaries.addAll(Arrays.asList(operator));
            return this;
        }

        @Override
        public ExpressionOperators.Builder addFunction(FunctionOperator... function) {
            this.functions.addAll(Arrays.asList(function));
            return this;
        }

        @Override
        public ExpressionOperators.Builder addConstant(Variable...constants) {
            this.constants.addAll(Arrays.asList(constants));
            return this;
        }

        @Override
        public ExpressionOperators build() {
            Map<String, UnaryOperator> unaryMap = new HashMap<>();
            Map<String, BinaryOperator> binariesMap = new HashMap<>();
            Map<String, FunctionOperator> functionsMap = new HashMap<>();
            Map<String, Variable> constantsMap = new HashMap<>();

            unaries.forEach(operator->unaryMap.put(operator.getIdentifier(), operator));
            binaries.forEach(operator->binariesMap.put(operator.getIdentifier(), operator));
            functions.forEach(operator->functionsMap.put(operator.getIdentifier(), operator));
            constants.forEach(constant->constantsMap.put(constant.getName(), constant));

            return new DefaultExpressionOperators(unaryMap,binariesMap,functionsMap,constantsMap);
        }
    }
}
