package com.xplusj.context;

import com.xplusj.ExpressionOperators;
import com.xplusj.operator.binary.BinaryOperator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.operator.unary.UnaryOperator;
import com.xplusj.Variable;

import static java.util.Objects.requireNonNull;

public class DefaultExpressionOperatorsAppender implements ExpressionOperators {

    private final ExpressionOperators parent;
    private final ExpressionOperators current;

    private DefaultExpressionOperatorsAppender(ExpressionOperators parent,
                                               ExpressionOperators current) {
        this.parent = parent;
        this.current = current;
    }

    @Override
    public boolean hasFunction(String name) {
        return current.hasFunction(name) || parent.hasFunction(name);
    }

    @Override
    public boolean hasBinaryOperator(String symbol) {
        return current.hasBinaryOperator(symbol) || parent.hasBinaryOperator(symbol);
    }

    @Override
    public boolean hasUnaryOperator(String symbol) {
        return current.hasUnaryOperator(symbol) || parent.hasUnaryOperator(symbol);
    }

    @Override
    public boolean hasConstant(String name) {
        return current.hasConstant(name) || parent.hasConstant(name);
    }

    @Override
    public BinaryOperator getBinaryOperator(String symbol) {
        if(current.hasBinaryOperator(symbol))
            return current.getBinaryOperator(symbol);

        return parent.getBinaryOperator(symbol);
    }

    @Override
    public UnaryOperator getUnaryOperator(String symbol) {
        if(current.hasUnaryOperator(symbol))
            return current.getUnaryOperator(symbol);

        return parent.getUnaryOperator(symbol);
    }

    @Override
    public FunctionOperator getFunction(String name) {
        if(current.hasFunction(name))
            return current.getFunction(name);

        return parent.getFunction(name);
    }

    @Override
    public Variable getConstant(String name) {
        if(current.hasConstant(name))
            return current.getConstant(name);

        return parent.getConstant(name);
    }

    @Override
    public ExpressionOperators append(ExpressionOperators newDefinitions) {
        return DefaultExpressionOperatorsAppender.append(this, newDefinitions);
    }

    public static ExpressionOperators append(ExpressionOperators parent,
                                             ExpressionOperators current){
        return new DefaultExpressionOperatorsAppender(
                requireNonNull(parent, "Parent instance definition is required"),
                requireNonNull(current, "Current instance definition is required")
        );
    }
}
