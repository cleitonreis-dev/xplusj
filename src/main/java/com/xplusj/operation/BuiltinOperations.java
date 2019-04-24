package com.xplusj.operation;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.BinaryOperator;
import com.xplusj.operation.operator.OperatorListBuilder;
import com.xplusj.operation.operator.UnaryOperator;

import java.util.List;
import java.util.Map;

import static com.xplusj.operation.Precedence.*;

public interface BuiltinOperations {

    static List<BinaryOperator> binaryOperators(){
        return OperatorListBuilder.binaryBuilder()
                .add('+', low(), c -> c.getFirstValue() + c.getSecondValue())
                .add('-', low(), c -> c.getFirstValue() - c.getSecondValue())
                .add('/', high(), c -> c.getFirstValue() / c.getSecondValue())
                .add('*', high(), c -> c.getFirstValue() * c.getSecondValue())
                .add('%', high(), c -> c.getFirstValue() % c.getSecondValue())
                .add('^', higherThan(high()), c -> Math.pow(c.getFirstValue(), c.getSecondValue()))
                .build();
    }

    static List<UnaryOperator> unaryOperators(){
        return OperatorListBuilder.unaryBuilder()
                .add('-', highest(), c-> -c.getValue())
                .add('+', highest(), c-> +c.getValue())
                .add('#', higherThan(high()), c-> Math.sqrt(c.getValue()))
                .build();
    }

    static List<ExpressionFunction> functions(){
        return ExpressionFunction
                .listBuilder()
                .add("max(a,b)", c->Math.max(c.param("a"),c.param("b")))
                .add("abs(a)", c->Math.abs(c.param("a")))
                .build();
    }


    static Map<String,Double> constants(){
        return ConstantMapBuilder.builder()
                .add("PI", Math.PI)
                .add("E", Math.E)
                .build();
    }
}
