package com.xplusj;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operation.operator.Operator;
import com.xplusj.operation.operator.UnaryOperatorRuntimeContext;

import static java.util.Objects.requireNonNull;

public interface ContextAppender extends Environment {

    ContextAppender append(Environment environment);

    static ContextAppender of(Environment parent, Environment current){
        return new DefaultContextAppender(parent,current);
    }

    class DefaultContextAppender implements ContextAppender{

        private final Environment parent;
        private final Environment current;
        private final boolean hasParent;

        DefaultContextAppender(Environment parent, Environment current) {
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
        public Operator<BinaryOperatorRuntimeContext> getBinaryOperator(char symbol) {
            Operator<BinaryOperatorRuntimeContext> operator = current.getBinaryOperator(symbol);

            if(operator == null && hasParent)
                operator = parent.getBinaryOperator(symbol);

            return operator;
        }

        @Override
        public Operator<UnaryOperatorRuntimeContext> getUnaryOperator(char symbol) {
            Operator<UnaryOperatorRuntimeContext> operator = current.getUnaryOperator(symbol);

            if(operator == null && hasParent)
                operator = parent.getUnaryOperator(symbol);

            return operator;
        }

        @Override
        public ExpressionFunction getFunction(String name) {
            ExpressionFunction function = current.getFunction(name);

            if(function == null && hasParent)
                function = parent.getFunction(name);

            return function;
        }

        @Override
        public Double getConstant(String name) {
            Double value = current.getConstant(name);

            if(value == null && hasParent)
                value = parent.getConstant(name);

            return value;
        }

        @Override
        public ContextAppender append(Environment environment) {
            return new DefaultContextAppender(this, environment);
        }
    }
}
