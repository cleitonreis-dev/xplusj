package com.xplusj.tokenizer;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.OperatorDefinition;

import java.util.Set;

public class DefaultExpressionTokenizer implements ExpressionTokenizer{

    private final ExpressionOperatorDefinitions operatorDefinitions;
    private final Set<OperatorDefinition<?>> allUnaryBinary;

    private DefaultExpressionTokenizer(final ExpressionOperatorDefinitions operatorDefinitions) {
        this.operatorDefinitions = operatorDefinitions;
        this.allUnaryBinary = operatorDefinitions.list(ExpressionOperatorDefinitions.ListOperatorFilter.UNARY_AND_BINARY);

    }

    @Override
    public Tokenizer tokenize(final String expression) {
        return new com.xplusj.tokenizer.Tokenizer(expression, operatorDefinitions, allUnaryBinary);
    }

    public static DefaultExpressionTokenizer create(ExpressionOperatorDefinitions operatorDefinitions){
        return new DefaultExpressionTokenizer(operatorDefinitions);
    }
}
