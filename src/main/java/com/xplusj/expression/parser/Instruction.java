package com.xplusj.expression.parser;

import com.xplusj.operation.Operation;
import com.xplusj.operation.OperationVisitor;

public interface Instruction {

    void execute(ParserContext parserContext);

    static Instruction pushValue(final Double value){
        return pc -> pc.valueStack.push(value);
    }

    static Instruction pushOperation(final Operation<?> operation){
        return pc -> pc.opStack.push(operation);
    }

    static Instruction pushConstant(final String constantName){
        return pc -> pc.valueStack.push(pc.env.getConstant(constantName));
    }

    static Instruction pushVar(final String name){
        return pc -> pc.valueStack.push(pc.vars.get(name));
    }

    static Instruction execOperation(){
        return pc -> pc.valueStack.push(pc.opStack.pull().accept(pc.visitor));
    }
}
