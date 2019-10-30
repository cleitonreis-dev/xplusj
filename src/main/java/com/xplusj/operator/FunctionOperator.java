package com.xplusj.operator;

import com.xplusj.operator.function.FunctionIdentifier;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Function;

@EqualsAndHashCode(of = "identifier", callSuper = true)
@ToString(of = {"identifier"}, callSuper = true)
public class FunctionOperator extends Operator<FunctionOperatorContext>{
    private static final Precedence PRECEDENCE = Precedence.lowerThan(Precedence.highest());

    private final FunctionIdentifier identifier;

    public FunctionOperator(FunctionIdentifier identifier, Function<FunctionOperatorContext, Double> function) {
        super(OperatorType.FUNCTION,PRECEDENCE,function);
        this.identifier = identifier;
    }

    public String getName(){
        return identifier.getName();
    }

    public int getParamsLength() {
        return identifier.getParamNames().size();
    }

    @Override
    public double execute(FunctionOperatorContext context) {
        return function.apply(context);
    }

    public int paramIndex(String name) {
        return identifier.getParamIndex(name);
    }

    public static FunctionOperator create(String name, Function<FunctionOperatorContext, Double> function) {
        return new FunctionOperator(FunctionIdentifier.create(name),function);
    }

    public static FunctionOperator create(String name, String function) {
        return new FunctionOperator(FunctionIdentifier.create(name),new CompiledFunction(function));
    }
}
