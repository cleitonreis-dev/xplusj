package com.xplusj.context;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.Constant;
import com.xplusj.operator.binary.BinaryOperatorDefinition;
import com.xplusj.operator.function.FunctionOperatorDefinition;
import com.xplusj.operator.unary.UnaryOperatorDefinition;

import static java.util.Objects.requireNonNull;

public class DefaultExpressionOperatorDefinitionsAppender implements ExpressionOperatorDefinitions {

    private final ExpressionOperatorDefinitions parent;
    private final ExpressionOperatorDefinitions current;

    private DefaultExpressionOperatorDefinitionsAppender(ExpressionOperatorDefinitions parent,
                                                        ExpressionOperatorDefinitions current) {
        this.parent = parent;
        this.current = current;
    }

    @Override
    public boolean hasFunction(String name) {
        return current.hasFunction(name) || parent.hasFunction(name);
    }

    @Override
    public boolean hasBinaryOperator(char symbol) {
        return current.hasBinaryOperator(symbol) || parent.hasBinaryOperator(symbol);
    }

    @Override
    public boolean hasUnaryOperator(char symbol) {
        return current.hasUnaryOperator(symbol) || parent.hasUnaryOperator(symbol);
    }

    @Override
    public boolean hasConstant(String name) {
        return current.hasConstant(name) || parent.hasConstant(name);
    }

    @Override
    public BinaryOperatorDefinition getBinaryOperator(char symbol) {
        if(current.hasBinaryOperator(symbol))
            return current.getBinaryOperator(symbol);

        return parent.getBinaryOperator(symbol);
    }

    @Override
    public UnaryOperatorDefinition getUnaryOperator(char symbol) {
        if(current.hasUnaryOperator(symbol))
            return current.getUnaryOperator(symbol);

        return parent.getUnaryOperator(symbol);
    }

    @Override
    public FunctionOperatorDefinition getFunction(String name) {
        if(current.hasFunction(name))
            return current.getFunction(name);

        return parent.getFunction(name);
    }

    @Override
    public Constant getConstant(String name) {
        if(current.hasConstant(name))
            return current.getConstant(name);

        return parent.getConstant(name);
    }

    @Override
    public ExpressionOperatorDefinitions append(ExpressionOperatorDefinitions newDefinitions) {
        return DefaultExpressionOperatorDefinitionsAppender.append(this, newDefinitions);
    }

    public static ExpressionOperatorDefinitions append(ExpressionOperatorDefinitions parent,
                                                       ExpressionOperatorDefinitions current){
        return new DefaultExpressionOperatorDefinitionsAppender(
                requireNonNull(parent, "Parent instance definition is required"),
                requireNonNull(current, "Current instance definition is required")
        );
    }
}
