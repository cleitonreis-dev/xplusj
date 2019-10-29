package com.xplusj.context;

import com.xplusj.*;
import com.xplusj.expression.FormulaExpression;
import com.xplusj.expression.InlineExpression;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class DefaultEnvironment implements Environment {

    private final ContextAppender context;
    private final ExpressionTokenizerFactory tokenizerFactory;
    private final ExpressionParserFactory expressionParserFactory;
    private final ExpressionParser parser;

    private DefaultEnvironment(ContextAppender context,
                               ExpressionTokenizerFactory tokenizerFactory,
                               ExpressionParserFactory expressionParserFactory) {
        this.context = context;
        this.tokenizerFactory = tokenizerFactory;
        this.expressionParserFactory = expressionParserFactory;

        ExpressionTokenizer tokenizer = tokenizerFactory.create(this);
        this.parser = expressionParserFactory.create(this, tokenizer);
    }

    @Override
    public Expression expression(String expression) {
        return new InlineExpression(expression, this);
    }

    @Override
    public Expression formula(String formula) {
        return new FormulaExpression(formula, this);
    }

    @Override
    public Environment appendContext(GlobalContext context) {
        ContextAppender appender = this.context.append(context);
        return new DefaultEnvironment(appender, tokenizerFactory, expressionParserFactory);
    }

    @Override
    public GlobalContext getContext() {
        return this.context;
    }

    @Override
    public ExpressionParser getParser() {
        return this.parser;
    }

    public static class Builder implements Environment.Builder{
        private GlobalContext context;
        private ExpressionParserFactory parserFactory = ExpressionParserFactory.defaultFactory();
        private ExpressionTokenizerFactory tokenizerFactory = ExpressionTokenizerFactory.defaultFactory();

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
        public Environment build() {
            ContextAppender appender = DefaultContextAppender.create(requireNonNull(context,
                ()->format("A %s instance is required to build the environment", GlobalContext.class.getSimpleName())
            ));

            return new DefaultEnvironment(appender, tokenizerFactory, parserFactory);
        }
    }

    public static Builder builder(){
        return new Builder();
    }
}
