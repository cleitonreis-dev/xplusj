package com.xplusj;

import com.xplusj.context.DefaultExpressionContext;
import com.xplusj.factory.*;
import com.xplusj.operator.binary.BinaryOperatorExecutor;
import com.xplusj.operator.function.FunctionOperatorExecutor;
import com.xplusj.operator.unary.UnaryOperatorExecutor;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.parser.tokenizer.ExpressionTokenizer;

public interface ExpressionContext {
    ExpressionOperators getDefinitions();

    UnaryOperatorExecutor getUnaryOperator(String symbol);

    BinaryOperatorExecutor getBinaryOperator(String symbol);

    FunctionOperatorExecutor getFunction(String name);

    ExpressionTokenizer getTokenizer();

    ExpressionParser getParser();

    Expression expression(String expression);

    Expression formula(String formula);

    ExpressionContext append(ExpressionOperators operatorDefinitions);

    interface Builder{
        Builder setOperatorDefinitions(ExpressionOperators definitions);

        Builder setExpressionFactory(ExpressionFactory expressionFactory);

        Builder setTokenizerFactory(ExpressionTokenizerFactory tokenizerFactory);

        Builder setParserFactory(ExpressionParserFactory parserFactory);

        Builder setUnaryOperatorFactory(ExpressionUnaryOperatorFactory unaryFactory);

        Builder setBinaryOperatorFactory(ExpressionBinaryOperatorFactory binaryFactory);

        Builder setFunctionOperatorFactory(ExpressionFunctionOperatorFactory functionFactory);

        ExpressionContext build();
    }

    static Builder builder(){
        return DefaultExpressionContext.builder();
    }
}
