package com.xplusj.expression;

import com.xplusj.operator.OperatorContext;
import com.xplusj.operator.Operator;
import com.xplusj.parser.ExpressionParserProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InstructionListProcessor
        implements ExpressionParserProcessor<List<Consumer<ExpressionParserProcessor>>> {

    private final List<Consumer<ExpressionParserProcessor>> instructions;
    private final Stack<Operator<?>> opStack;

    private InstructionListProcessor(final Stack<Operator<?>> opStack) {
        this.instructions = new ArrayList<>();
        this.opStack = opStack;
    }

    @Override
    public void addValue(double value) {
        instructions.add(p->p.addValue(value));
    }

    @Override
    public void addVar(String value) {
        instructions.add(p->p.addVar(value));
    }

    @Override
    public void addConstant(String name) {
        instructions.add(p->p.addConstant(name));
    }

    @Override
    public void addOperator(Operator<? extends OperatorContext> operator) {
        opStack.push(operator);
        instructions.add(p->p.addOperator(operator));
    }

    @Override
    public void callLastOperatorAndAddResult() {
        opStack.pull();
        instructions.add(ExpressionParserProcessor::callLastOperatorAndAddResult);
    }

    @Override
    public void callLastOperatorAndAddResult(int totalOfParamsToRead) {
        opStack.pull();
        instructions.add((parserProcessor)->parserProcessor.callLastOperatorAndAddResult(totalOfParamsToRead));
    }

    @Override
    public Operator<?> getLastOperator() {
        return opStack.peek();
    }

    @Override
    public List<Consumer<ExpressionParserProcessor>> getResult() {
        return instructions;
    }

    static InstructionListProcessor create(Stack<Operator<?>> opStack){
        return new InstructionListProcessor(opStack);
    }

    public static InstructionListProcessor create(){
        return create(Stack.instance());
    }
}
