package com.xplusj;

import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.parser.ExpressionParser;
import com.xplusj.tokenizer.ExpressionTokenizer;

public interface ExpressionContext {
    ExpressionOperatorDefinitions getDefinitions();

    UnaryOperator getUnaryOperator(String symbol);

    BinaryOperator getBinaryOperator(String symbol);

    FunctionOperator getFunction(String name);

    ExpressionTokenizer getTokenizer();

    ExpressionParser getParser();

    interface Builder<T extends ExpressionContext>{
        Builder<T> setOperatorDefinitions(ExpressionOperatorDefinitions definitions);

        T build();
    }
}
