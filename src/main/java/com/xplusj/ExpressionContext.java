package com.xplusj;

import com.xplusj.operator.binary.BinaryOperatorExecutor;
import com.xplusj.operator.function.FunctionOperatorExecutor;
import com.xplusj.operator.unary.UnaryOperatorExecutor;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionContext {
    ExpressionOperatorDefinitions getDefinitions();

    UnaryOperatorExecutor getUnaryOperator(String symbol);

    BinaryOperatorExecutor getBinaryOperator(String symbol);

    FunctionOperatorExecutor getFunction(String name);

    ExpressionTokenizer getTokenizer();

    ExpressionParser getParser();

    interface Builder<T extends ExpressionContext>{
        Builder<T> setOperatorDefinitions(ExpressionOperatorDefinitions definitions);

        T build();
    }
}
