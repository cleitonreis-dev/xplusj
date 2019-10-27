package com.xplusj.expression;

import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.interpreter.ExpressionInterpreterProcessor;
import com.xplusj.interpreter.stack.Stack;
import com.xplusj.operator.*;

public class TwoStackBasedInterpreter implements ExpressionInterpreterProcessor {

    private final GlobalContext globalContext;
    private final VariableContext variableContext;
    private final Stack<Double> valueStack;
    private final Stack<Operator<?>> opStack;

    TwoStackBasedInterpreter(GlobalContext globalContext, VariableContext variableContext, Stack<Double> valueStack, Stack<Operator<?>> opStack) {
        this.globalContext = globalContext;
        this.variableContext = variableContext;
        this.valueStack = valueStack;
        this.opStack = opStack;
    }

    @Override
    public void pushValue(double value) {
        valueStack.push(value);
    }

    @Override
    public void pushVar(String value) {
        if(variableContext.contains(value)) {
            valueStack.push(variableContext.value(value));
            return;
        }

        //TODO create specialized exception
        throw new IllegalStateException("Variable '"+ value +"' not found");
    }

    @Override
    public void pushConstant(String name) {
        if(globalContext.hasConstant(name)) {
            valueStack.push(globalContext.getConstant(name));
            return;
        }

        //TODO create specialized exception
        throw new IllegalStateException("Constant '"+ name +"' not found");
    }

    @Override
    public void pushOperator(Operator<? extends OperatorContext> operator) {
        opStack.push(operator);
    }

    @Override
    public void callLastOperatorAndPushResult() {
        Operator<? extends OperatorContext> operator = opStack.pull();
        double value = operator.execute(getContext(operator));
        valueStack.push(value);
    }

    @Override
    public Operator<?> getLastOperator() {
        return opStack.peek();
    }

    public double getCalculatedResult(){
        return valueStack.pull();
    }

    static TwoStackBasedInterpreter create(GlobalContext globalContext, VariableContext variableContext, Stack<Double> valueStack, Stack<Operator<?>> opStack){
        return new TwoStackBasedInterpreter(globalContext,variableContext,valueStack,opStack);
    }

    private <Context extends OperatorContext> Context getContext(Operator<?> operator){
        OperatorContext context;
        if(operator.getType() == OperatorType.UNARY)
            context = new UnaryOperatorContext(globalContext,getParams(operator)[0]);
        else if(operator.getType() == OperatorType.BINARY)
            context = new BinaryOperatorContext(globalContext,getParams(operator));
        else if(operator.getType() == OperatorType.FUNCTION)
            context = new FunctionOperatorContext((FunctionOperator)operator,globalContext,getParams(operator));
        else
            //TODO create proper exception
            throw new IllegalArgumentException("Invalid operator type" + operator.getType());

        return (Context) context;
    }

    private double[] getParams(Operator<?> operator){
        double[] values = new double[operator.getParamsLength()];

        for(int i = values.length - 1; i >= 0; i--)
            values[i] = valueStack.pull();

        return values;
    }
}
