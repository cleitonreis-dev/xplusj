package com.xplusj.expression;

import com.xplusj.interpreter.ExpressionInterpreterProcessor;
import com.xplusj.interpreter.stack.Stack;
import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InstructionListInterpreter implements ExpressionInterpreterProcessor {

    private final List<Consumer<ExpressionInterpreterProcessor>> instructions = new ArrayList<>();
    private final Stack<Operator<?>> opStack = Stack.defaultStack();

    @Override
    public void pushValue(double value) {
        instructions.add(p->p.pushValue(value));
    }

    @Override
    public void pushVar(String value) {
        instructions.add(p->p.pushVar(value));
    }

    @Override
    public void pushConstant(String name) {
        instructions.add(p->p.pushConstant(name));
    }

    @Override
    public void pushOperator(Operator<? extends OperatorContext> operator) {
        opStack.push(operator);
        instructions.add(p->p.pushOperator(operator));
    }

    @Override
    public void callLastOperatorAndPushResult() {
        opStack.pull();
        instructions.add(ExpressionInterpreterProcessor::callLastOperatorAndPushResult);
    }

    @Override
    public Operator<?> getLastOperator() {
        return opStack.peek();
    }

    public List<Consumer<ExpressionInterpreterProcessor>> getInstructions() {
        return instructions;
    }
}
