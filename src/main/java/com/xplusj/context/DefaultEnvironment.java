package com.xplusj.context;

import com.xplusj.*;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class DefaultEnvironment implements Environment {

    private final ContextAppender context;
    private final ExpressionTokenizerFactory tokenizerFactory;
    private final ExpressionParserFactory parserFactory;
    private final ExpressionFactory expressionFactory;

    private DefaultEnvironment(ContextAppender context,
                               ExpressionTokenizerFactory tokenizerFactory,
                               ExpressionParserFactory expressionParserFactory,
                               ExpressionFactory expressionFactory) {
        this.context = context;
        this.tokenizerFactory = tokenizerFactory;
        this.parserFactory = expressionParserFactory;
        this.expressionFactory = expressionFactory;
    }

    @Override
    public Expression expression(String expression) {
        return expressionFactory.expression(expression, this);
    }

    @Override
    public Expression formula(String formula) {
        return expressionFactory.formula(formula, this);
    }

    @Override
    public Environment appendContext(GlobalContext context) {
        ContextAppender appender = this.context.append(context);
        return new DefaultEnvironment(appender, tokenizerFactory, parserFactory, expressionFactory);
    }

    @Override
    public GlobalContext getContext() {
        return this.context;
    }

    @Override
    public ExpressionParser getParser() {
        return parserFactory.create(this);
    }

    @Override
    public ExpressionTokenizer getTokenizer() {
        return tokenizerFactory.create(this);
    }

    public static class Builder implements Environment.Builder{
        private GlobalContext context;
        private ExpressionParserFactory parserFactory = ExpressionParserFactory.defaultFactory();
        private ExpressionTokenizerFactory tokenizerFactory = ExpressionTokenizerFactory.defaultFactory();
        private ExpressionFactory expressionFactory = ExpressionFactory.defaultFactory();

        @Override
        public Environment.Builder setContext(GlobalContext context) {
            this.context = context;
            return this;
        }

        @Override
        public Environment.Builder setParserFactory(ExpressionParserFactory parserFactory) {
            this.parserFactory = parserFactory;
            return this;
        }

        @Override
        public Environment.Builder setTokenizerFactory(ExpressionTokenizerFactory tokenizerFactory) {
            this.tokenizerFactory = tokenizerFactory;
            return this;
        }

        @Override
        public Environment.Builder setExpressionFactory(ExpressionFactory expressionFactory) {
            this.expressionFactory = expressionFactory;
            return this;
        }

        @Override
        public Environment build() {
            ContextAppender appender = DefaultContextAppender.create(requireNonNull(context,
                ()->format("A %s instance is required to build the environment", GlobalContext.class.getSimpleName())
            ));

            return new DefaultEnvironment(appender, tokenizerFactory, parserFactory, expressionFactory);
        }
    }

    public static Builder builder(){
        return new Builder();
    }
}
