package com.xplusj.operation;

import com.xplusj.operation.function.ExpressionFunction;
import com.xplusj.operation.operator.BinaryOperator;
import com.xplusj.operation.operator.UnaryOperator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xplusj.operation.Precedence.*;
import static com.xplusj.operation.function.ExpressionFunction.function;
import static com.xplusj.operation.operator.Operator.binary;
import static com.xplusj.operation.operator.Operator.unary;
import static java.util.Arrays.asList;

public interface BuiltinOperations {

    static List<BinaryOperator> binaryOperators(){
        return asList(
            binary('+', low(), c -> c.getFirstValue() + c.getSecondValue()),
            binary('-', low(), c -> c.getFirstValue() - c.getSecondValue()),
            binary('/', high(), c -> c.getFirstValue() / c.getSecondValue()),
            binary('*', high(), c -> c.getFirstValue() * c.getSecondValue()),
            binary('%', high(), c -> c.getFirstValue() % c.getSecondValue()),
            binary('^', higherThan(high()), c -> Math.pow(c.getFirstValue(), c.getSecondValue()))
        );
    }

    static List<UnaryOperator> unaryOperators(){
        return asList(
            unary('-', highest(), c-> -c.getValue()),
            unary('+', highest(), c-> +c.getValue()),
            unary('#', higherThan(high()), c-> Math.sqrt(c.getValue()))

        );
    }

    static List<ExpressionFunction> functions(){
        return asList(
            function("max(a,b)", c->Math.max(c.param("a"),c.param("b"))),
            function("abs(a)", c->Math.abs(c.param("a")))
        );
    }


    static Map<String,Double> constants(){
        Map<String,Double> constants = new HashMap<>();
        constants.put("PI", Math.PI);
        constants.put("E", Math.E);

        return constants;
    }
}
