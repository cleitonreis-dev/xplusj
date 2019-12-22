package com.xplusj.context;

import com.xplusj.Expression;
import com.xplusj.ExpressionGlobalContext;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.factory.*;
import com.xplusj.operator.Operators;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public class DefaultExpressionGlobalContext implements ExpressionGlobalContext {

    private final ExpressionOperatorDefinitions definitions;
    private final ExpressionParser parser;
    private final ExpressionTokenizer tokenizer;
    private final ExpressionFactory expressionFactory;
    private final ExpressionParserFactory parserFactory;
    private final ExpressionTokenizerFactory tokenizerFactory;
    private final ExpressionUnaryOperatorFactory unaryFactory;
    private final ExpressionBinaryOperatorFactory binaryFactory;
    private final ExpressionFunctionOperatorFactory functionFactory;

    private DefaultExpressionGlobalContext(ExpressionOperatorDefinitions definitions,
                                           ExpressionFactory expressionFactory,
                                           ExpressionParserFactory parserFactory,
                                           ExpressionTokenizerFactory tokenizerFactory,
                                           ExpressionUnaryOperatorFactory unaryFactory,
                                           ExpressionBinaryOperatorFactory binaryFactory,
                                           ExpressionFunctionOperatorFactory functionFactory) {
        this.definitions = definitions;
        this.tokenizer = tokenizerFactory.create(definitions);
        this.parser = parserFactory.create(this.tokenizer, definitions);
        this.expressionFactory = expressionFactory;
        this.parserFactory = parserFactory;
        this.tokenizerFactory = tokenizerFactory;
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
    public ExpressionOperatorDefinitions getDefinitions() {
        return definitions;
    }

    @Override
    public UnaryOperator getUnaryOperator(char symbol) {
        return unaryFactory.create(definitions.getUnaryOperator(symbol), this);
    }

    @Override
    public BinaryOperator getBinaryOperator(char symbol) {
        return binaryFactory.create(definitions.getBinaryOperator(symbol), this);
    }

    @Override
    public FunctionOperator getFunction(String name) {
        return functionFactory.create(definitions.getFunction(name),this);
    }

    @Override
    public ExpressionTokenizer getTokenizer() {
        return tokenizer;
    }

    @Override
    public ExpressionParser getParser() {
        return parser;
    }

    @Override
    public ExpressionGlobalContext append(ExpressionOperatorDefinitions operatorDefinitions) {
        return new DefaultExpressionGlobalContext(
                definitions.append(operatorDefinitions), expressionFactory, parserFactory,
                tokenizerFactory, unaryFactory, binaryFactory, functionFactory
        );
    }

    public static Builder builder(){
        return new Builder();
    }

    static class Builder implements ExpressionGlobalContext.Builder{

        private ExpressionOperatorDefinitions definitions;
        private ExpressionFactory expressionFactory;
        private ExpressionParserFactory parserFactory;
        private ExpressionTokenizerFactory tokenizerFactory;
        private ExpressionUnaryOperatorFactory unaryFactory;
        private ExpressionBinaryOperatorFactory binaryFactory;
        private ExpressionFunctionOperatorFactory functionFactory;


        @Override
        public ExpressionGlobalContext.Builder setExpressionFactory(ExpressionFactory expressionFactory) {
            this.expressionFactory = expressionFactory;
            return this;
        }

        @Override
        public ExpressionGlobalContext.Builder setParserFactory(ExpressionParserFactory parserFactory) {
            this.parserFactory = parserFactory;
            return this;
        }

        @Override
        public ExpressionGlobalContext.Builder setTokenizerFactory(ExpressionTokenizerFactory tokenizerFactory) {
            this.tokenizerFactory = tokenizerFactory;
            return this;
        }

        @Override
        public ExpressionGlobalContext.Builder setUnaryOperatorFactory(ExpressionUnaryOperatorFactory unaryFactory) {
            this.unaryFactory = unaryFactory;
            return this;
        }

        @Override
        public ExpressionGlobalContext.Builder setBinaryOperatorFactory(ExpressionBinaryOperatorFactory binaryFactory) {
            this.binaryFactory = binaryFactory;
            return this;
        }

        @Override
        public ExpressionGlobalContext.Builder setFunctionOperatorFactory(ExpressionFunctionOperatorFactory functionFactory) {
            this.functionFactory = functionFactory;
            return this;
        }

        @Override
        public ExpressionGlobalContext.Builder setOperatorDefinitions(ExpressionOperatorDefinitions definitions) {
            this.definitions = definitions;
            return this;
        }

        @Override
        public ExpressionGlobalContext build() {
            initDefaults();

            return new DefaultExpressionGlobalContext(
                definitions,expressionFactory, parserFactory,
                tokenizerFactory,unaryFactory, binaryFactory, functionFactory
            );
        }

        private void initDefaults(){
            if(definitions == null)
                definitions = ExpressionOperatorDefinitions.builder()
                        .addConstant(Operators.Constants.CONSTANTS)
                        .addUnaryOperator(Operators.Unaries.OPERATORS)
                        .addBinaryOperator(Operators.Binaries.OPERATORS)
                        .addFunction(Operators.Functions.FUNCTIONS)
                        .build();

            if(expressionFactory == null)
                expressionFactory = ExpressionFactory.defaultFactory();

            if(parserFactory == null)
                parserFactory = ExpressionParserFactory.defaultFactory();

            if(tokenizerFactory == null)
                tokenizerFactory = ExpressionTokenizerFactory.defaultFactory();

            if(unaryFactory == null)
                unaryFactory = ExpressionUnaryOperatorFactory.defaultFactory();

            if(binaryFactory == null)
                binaryFactory = ExpressionBinaryOperatorFactory.defaultFactory();

            if(functionFactory == null)
                functionFactory = ExpressionFunctionOperatorFactory.defaultFactory();
        }
    }
}
