package com.xplusj;

import com.xplusj.context.DefaultExpressionGlobalContext;
import com.xplusj.factory.ExpressionFactory;
import com.xplusj.factory.ExpressionParserFactory;
import com.xplusj.factory.ExpressionTokenizerFactory;
import com.xplusj.factory.*;

public interface ExpressionGlobalContext extends ExpressionContext{

    Expression expression(String expression);

    Expression formula(String formula);

    ExpressionGlobalContext append(ExpressionOperatorDefinitions operatorDefinitions);

    interface Builder extends ExpressionContext.Builder<ExpressionGlobalContext>{

        ExpressionGlobalContext.Builder setExpressionFactory(ExpressionFactory expressionFactory);

        ExpressionGlobalContext.Builder setParserFactory(ExpressionParserFactory parserFactory);

        ExpressionGlobalContext.Builder setTokenizerFactory(ExpressionTokenizerFactory tokenizerFactory);

        ExpressionGlobalContext.Builder setUnaryOperatorFactory(ExpressionUnaryOperatorFactory unaryFactory);

        ExpressionGlobalContext.Builder setBinaryOperatorFactory(ExpressionBinaryOperatorFactory binaryFactory);

        ExpressionGlobalContext.Builder setFunctionOperatorFactory(ExpressionFunctionOperatorFactory functionFactory);
    }

    static ExpressionGlobalContext.Builder builder(){
        return DefaultExpressionGlobalContext.builder();
    }
}
