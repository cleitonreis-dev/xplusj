package com.xplusj.operator.function;

import com.xplusj.operator.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.function.Function;

@EqualsAndHashCode(of = "identifier", callSuper = true)
@ToString(of = {"identifier"}, callSuper = true)
public class FunctionOperatorDefinition extends OperatorDefinition<FunctionOperatorContext> {
    private static final Precedence PRECEDENCE = Precedence.lowerThan(Precedence.highest());

    private final FunctionIdentifier identifier;
    private final boolean varArgs;

    public FunctionOperatorDefinition(FunctionIdentifier identifier, Function<FunctionOperatorContext, Double> function) {
        super(identifier.getName(), OperatorType.FUNCTION,PRECEDENCE,function,identifier.getParamsLength());
        this.identifier = identifier;
        this.varArgs = identifier.getParamsLength() > 0
                && identifier.getParams().get(identifier.getParamsLength() - 1).isVarArgs();
    }

    public String getName(){
        return identifier.getName();
    }

    public int getParamsLength() {
        return identifier.getParamsLength();
    }

    public int paramIndex(String name) {
        return identifier.getParamIndex(name);
    }

    public List<FunctionParam> getParams(){
        return identifier.getParams();
    }

    public boolean isVarArgs(){
        return this.varArgs;
    }

    public static FunctionOperatorDefinition create(String name, Function<FunctionOperatorContext, Double> function) {
        return new FunctionOperatorDefinition(FunctionIdentifier.create(name),function);
    }

    public static FunctionOperatorDefinition create(String name, String function) {
        return new FunctionOperatorDefinition(FunctionIdentifier.create(name),new CompiledFunction(function));
    }
}
