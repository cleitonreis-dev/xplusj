package com.xplusj.context;

import com.xplusj.core.ContextAppender;
import com.xplusj.core.GlobalContext;
import com.xplusj.core.operator.*;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class DefaultContextAppender implements ContextAppender {

    private final GlobalContext parent;
    private final GlobalContext current;
    private final boolean hasParent;

    public DefaultContextAppender(GlobalContext parent, GlobalContext current) {
        this.parent = parent;
        this.current = requireNonNull(current,"current context is required");
        this.hasParent = parent != null;
    }

    @Override
    public boolean hasFunction(String name) {
        return current.hasFunction(name)
                || (hasParent && parent.hasFunction(name));
    }

    @Override
    public boolean hasBinaryOperator(char symbol) {
        return current.hasBinaryOperator(symbol)
                || (hasParent && parent.hasBinaryOperator(symbol));
    }

    @Override
    public boolean hasUnaryOperator(char symbol) {
        return current.hasUnaryOperator(symbol)
                || (hasParent && parent.hasUnaryOperator(symbol));
    }

    @Override
    public boolean hasConstant(String name) {
        return current.hasConstant(name)
                || (hasParent && parent.hasConstant(name));
    }

    @Override
    public BinaryOperator getBinaryOperator(char symbol) {
        BinaryOperator operator = current.getBinaryOperator(symbol);

        if(operator == null && hasParent)
            operator = parent.getBinaryOperator(symbol);

        return operator;
    }

    @Override
    public UnaryOperator getUnaryOperator(char symbol) {
        UnaryOperator operator = current.getUnaryOperator(symbol);

        if(operator == null && hasParent)
            operator = parent.getUnaryOperator(symbol);

        return operator;
    }

    @Override
    public FunctionOperator getFunction(String name) {
        FunctionOperator function = current.getFunction(name);

        if(function == null && hasParent)
            function = parent.getFunction(name);

        return function;
    }

    @Override
    public double getConstant(String name) {
        if(current.hasConstant(name))
            return current.getConstant(name);

        if(parent.hasConstant(name))
            return parent.getConstant(name);

        throw new IllegalArgumentException(format("Constant '%s' not found ", name));
    }

    @Override
    public ContextAppender append(GlobalContext context) {
        return new DefaultContextAppender(this, context);
    }
}
