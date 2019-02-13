package com.xplusj;

import com.xplusj.function.ExpressionFunction;
import com.xplusj.operator.BinaryOperatorRuntimeContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.UnaryOperatorRuntimeContext;

import java.util.List;

import static com.xplusj.OperationPrecedence.*;
import static com.xplusj.function.ExpressionFunction.function;
import static com.xplusj.operator.Operator.binary;
import static com.xplusj.operator.Operator.unary;
import static java.util.Arrays.asList;

public interface BuiltinOperations {

    static List<Operator<BinaryOperatorRuntimeContext>> binaryOperators(){
        return asList(
            binary('+', low(), c -> c.getFirstValue() + c.getSecondValue()),
            binary('-', low(), c -> c.getFirstValue() - c.getSecondValue()),
            binary('/', high(), c -> c.getFirstValue() / c.getSecondValue()),
            binary('*', high(), c -> c.getFirstValue() * c.getSecondValue()),
            binary('%', high(), c -> c.getFirstValue() % c.getSecondValue()),
            binary('^', higherThan(high()), c -> Math.pow(c.getFirstValue(), c.getSecondValue()))
        );
    }

    static List<Operator<UnaryOperatorRuntimeContext>> unaryOperators(){
        return asList(
            unary('-', higher(), c-> -c.getValue()),
            unary('+', higher(), c-> +c.getValue()),
            unary('#', higherThan(high()), c-> Math.sqrt(c.getValue()))

        );
    }

    static List<ExpressionFunction> functions(){
        return asList(
            function("max(a,b)", c->Math.max(c.getVar("a"),c.getVar("b"))),
            function("abs(a)", c->Math.abs(c.getVar("a")))
        );
    }
}
