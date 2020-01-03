package com.xplusj.context;

import com.xplusj.Constants;
import com.xplusj.Expression;
import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperators;
import com.xplusj.factory.*;
import com.xplusj.operator.Functions;
import com.xplusj.operator.Operators;
import com.xplusj.operator.binary.BinaryOperatorExecutor;
import com.xplusj.operator.function.FunctionOperatorExecutor;
import com.xplusj.operator.unary.UnaryOperatorExecutor;
import com.xplusj.parser.ExpressionParser;

public class DefaultExpressionContext implements ExpressionContext {

    private final ExpressionOperators definitions;
    private final ExpressionParser parser;
    private final ExpressionFactory expressionFactory;
    private final ExpressionParserFactory parserFactory;
    private final ExpressionUnaryOperatorFactory unaryFactory;
    private final ExpressionBinaryOperatorFactory binaryFactory;
    private final ExpressionFunctionOperatorFactory functionFactory;

    private DefaultExpressionContext(ExpressionOperators definitions,
                                     ExpressionFactory expressionFactory,
                                     ExpressionParserFactory parserFactory,
                                     ExpressionUnaryOperatorFactory unaryFactory,
                                     ExpressionBinaryOperatorFactory binaryFactory,
                                     ExpressionFunctionOperatorFactory functionFactory) {
        this.definitions = definitions;
        this.parser = parserFactory.create(this);
        this.expressionFactory = expressionFactory;
        this.parserFactory = parserFactory;
        this.unaryFactory = unaryFactory;
        this.binaryFactory = binaryFactory;
        this.functionFactory = functionFactory;
    }

    @Override
    public Expression expression(String expression) {
        return expressionFactory.expression(expression, this);
    }

    @Override
    public Expression formula(String formula) {
        return expressionFactory.formula(formula,this);
    }

    @Override
    public ExpressionOperators getDefinitions() {
        return definitions;
    }

    @Override
    public UnaryOperatorExecutor getUnaryOperator(String symbol) {
        return unaryFactory.create(definitions.getUnaryOperator(symbol), this);
    }

    @Override
    public BinaryOperatorExecutor getBinaryOperator(String symbol) {
        return binaryFactory.create(definitions.getBinaryOperator(symbol), this);
    }

    @Override
    public FunctionOperatorExecutor getFunction(String name) {
        return functionFactory.create(definitions.getFunction(name),this);
    }

    @Override
    public ExpressionParser getParser() {
        return parser;
    }

    @Override
    public ExpressionContext append(ExpressionOperators operatorDefinitions) {
        return new DefaultExpressionContext(
                definitions.append(operatorDefinitions), expressionFactory,
                parserFactory, unaryFactory, binaryFactory, functionFactory
        );
    }

    public static Builder builder(){
        return new Builder();
    }

    static class Builder implements ExpressionContext.Builder{

        private ExpressionOperators definitions;
        private ExpressionFactory expressionFactory;
        private ExpressionParserFactory parserFactory;
        private ExpressionUnaryOperatorFactory unaryFactory;
        private ExpressionBinaryOperatorFactory binaryFactory;
        private ExpressionFunctionOperatorFactory functionFactory;


        @Override
        public Builder setExpressionFactory(ExpressionFactory expressionFactory) {
            this.expressionFactory = expressionFactory;
            return this;
        }

        @Override
        public Builder setParserFactory(ExpressionParserFactory parserFactory) {
            this.parserFactory = parserFactory;
            return this;
        }

        @Override
        public Builder setUnaryOperatorFactory(ExpressionUnaryOperatorFactory unaryFactory) {
            this.unaryFactory = unaryFactory;
            return this;
        }

        @Override
        public Builder setBinaryOperatorFactory(ExpressionBinaryOperatorFactory binaryFactory) {
            this.binaryFactory = binaryFactory;
            return this;
        }

        @Override
        public Builder setFunctionOperatorFactory(ExpressionFunctionOperatorFactory functionFactory) {
            this.functionFactory = functionFactory;
            return this;
        }

        @Override
        public Builder setOperatorDefinitions(ExpressionOperators definitions) {
            this.definitions = definitions;
            return this;
        }

        @Override
        public ExpressionContext build() {
            initDefaults();

            return new DefaultExpressionContext(
                definitions,expressionFactory, parserFactory,
                unaryFactory, binaryFactory, functionFactory
            );
        }

        private void initDefaults(){
            if(definitions == null)
                definitions = ExpressionOperators.builder()
                        .addConstant(Constants.CONSTANTS)
                        .addUnaryOperator(Operators.Unary.OPERATORS)
                        .addBinaryOperator(Operators.Binary.OPERATORS)
                        .addFunction(Functions.FUNCTIONS)
                        .build();

            if(expressionFactory == null)
                expressionFactory = ExpressionFactory.defaultFactory();

            if(parserFactory == null)
                parserFactory = ExpressionParserFactory.defaultFactory();

            if(unaryFactory == null)
                unaryFactory = ExpressionUnaryOperatorFactory.defaultFactory();

            if(binaryFactory == null)
                binaryFactory = ExpressionBinaryOperatorFactory.defaultFactory();

            if(functionFactory == null)
                functionFactory = ExpressionFunctionOperatorFactory.defaultFactory();
        }
    }
}
