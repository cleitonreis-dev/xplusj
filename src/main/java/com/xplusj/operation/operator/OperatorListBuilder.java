package com.xplusj.operation.operator;

import com.xplusj.operation.Precedence;
import com.xplusj.operation.RuntimeContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class OperatorListBuilder<O extends Operator<?>, C extends RuntimeContext> {
    private List<O> operators = new ArrayList<>();

    public OperatorListBuilder<O,C> add(O operator){
        operators.add(operator);
        return this;
    }

    public abstract OperatorListBuilder<O,C> add(char symbol, Precedence precedence, Function<C,Double> function);

    public List<O> build(){
        return operators;
    }

    public static UnaryOperatorListBuilder unaryBuilder(){
        return new UnaryOperatorListBuilder();
    }

    public static BinaryOperatorListBuilder binaryBuilder(){
        return new BinaryOperatorListBuilder();
    }

    public static class UnaryOperatorListBuilder extends
            OperatorListBuilder<UnaryOperator,UnaryOperatorRuntimeContext>{

        @Override
        public OperatorListBuilder<UnaryOperator,UnaryOperatorRuntimeContext> add(
                char symbol, Precedence precedence, Function<UnaryOperatorRuntimeContext,Double> function) {
            return add(new UnaryOperator(symbol,precedence,function));
        }
    }

    public static class BinaryOperatorListBuilder extends
            OperatorListBuilder<BinaryOperator,BinaryOperatorRuntimeContext>{

        @Override
        public OperatorListBuilder<BinaryOperator,BinaryOperatorRuntimeContext> add(
                char symbol, Precedence precedence, Function<BinaryOperatorRuntimeContext,Double> function) {
            return add(new BinaryOperator(symbol,precedence,function));
        }
    }
}
