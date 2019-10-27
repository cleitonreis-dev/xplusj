package com.xplusj.operator;

import com.xplusj.Expression;
import com.xplusj.expression.FormulaExpression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompiledFunction implements Function<FunctionOperatorContext,Double> {

    private final String name;
    private final Expression expression;

    public CompiledFunction(String name, String expression) {
        this.name = name;
        this.expression = new FormulaExpression(null,null,null);
    }

    @Override
    public Double apply(FunctionOperatorContext functionOperatorContext) {

        //functionOperatorContext.
        return null;
    }

    public static void main(String[] args) {
        /*Stream<Object> stream = null;
stream.reduce()
        Optional.empty().get
        BigDecimal one = new BigDecimal(-0.1D);
        System.out.println(one.compareTo(BigDecimal.ZERO));
        BigDecimal.ZERO.multiply()

        Map<String,Object> map = null;
        String.join()*/

    }
}
