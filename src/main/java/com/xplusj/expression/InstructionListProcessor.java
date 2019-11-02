package com.xplusj.expression;

import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorContext;
import com.xplusj.parser.ExpressionParserProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InstructionListProcessor implements ExpressionParserProcessor {

    private final List<Consumer<ExpressionParserProcessor>> instructions = new ArrayList<>();
    private final Stack<Operator<?>> opStack = Stack.instance();

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
    public Operator<?> getLastOperator() {
        return opStack.peek();
    }

    List<Consumer<ExpressionParserProcessor>> getInstructions() {
        return instructions;
    }
}
