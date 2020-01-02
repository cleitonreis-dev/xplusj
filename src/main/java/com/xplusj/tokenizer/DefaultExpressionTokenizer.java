package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperators;
import com.xplusj.operator.Operators;

import java.util.HashSet;
import java.util.Set;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{
    private final Set<String> definedOperators;

    private DefaultExpressionTokenizer(final ExpressionOperators operatorDefinitions) {
        this.definedOperators = new HashSet<String>(){
            @Override public boolean contains(Object o) {
                String symbol = (String)o;
                return operatorDefinitions.hasBinaryOperator(symbol)
                        || operatorDefinitions.hasUnaryOperator(symbol);
            }
        };
    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.tokenizer.Tokenizer(expression,
                Operators.ALLOWED_OPERATOR_SYMBOLS, definedOperators);
    }

    public static DefaultExpressionTokenizer create(ExpressionOperators operatorDefinitions){
        return new DefaultExpressionTokenizer(operatorDefinitions);
    }
}
