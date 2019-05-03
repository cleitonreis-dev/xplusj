package com.xplusj.engine.processors;

import com.xplusj.core.GlobalContext;
import com.xplusj.core.VariableContainer;
import com.xplusj.core.operator.FunctionOperator;
import com.xplusj.core.operator.Operator;
import com.xplusj.core.operator.OperatorContext;
import com.xplusj.core.operator.OperatorType;
import com.xplusj.engine.parser.ExpressionInstructionsProcessor;
import com.xplusj.operator.BinaryOperatorContext;
import com.xplusj.operator.FunctionOperatorContext;
import com.xplusj.operator.UnaryOperatorContext;
import com.xplusj.stack.Stack;

public class ExpressionInterpreterProcessor implements ExpressionInstructionsProcessor {

    private final Stack<Double> valueStack = Stack.defaultStack();
    private final Stack<Operator<?>> opStack = Stack.defaultStack();
    private final GlobalContext globalContext;
    private final VariableContainer variableContainer;

    public ExpressionInterpreterProcessor(GlobalContext globalContext, VariableContainer variableContainer) {
        this.globalContext = globalContext;
        this.variableContainer = variableContainer;
    }

    @Override
    public void pushValue(double value) {
        valueStack.push(value);
    }

    @Override
    public void pushVar(String value) {
        if(variableContainer.contains(value))
            valueStack.push(variableContainer.value(value));

        //TODO create specialized exception
        throw new IllegalStateException("Variable '"+ value +"' not found");
    }

    @Override
    public void pushConstant(String name) {
        if(globalContext.hasConstant(name))
            valueStack.push(globalContext.getConstant(name));

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

    private <Context extends OperatorContext> Context getContext(Operator<?> operator){
        OperatorContext context;
        if(operator.geType() == OperatorType.UNARY)
            context = new UnaryOperatorContext(globalContext,getParams(operator)[0]);
        else if(operator.geType() == OperatorType.BINARY)
            context = new BinaryOperatorContext(globalContext,getParams(operator));
        else if(operator.geType() == OperatorType.FUNCTION)
            context = new FunctionOperatorContext((FunctionOperator)operator,globalContext,getParams(operator));
        else
            //TODO create proper exception
            throw new IllegalArgumentException("Invalid operator type" + operator.geType());

        return (Context) context;
    }

    private double[] getParams(Operator<?> operator){
        double[] values = new double[operator.getParamsLength()];

        for(int i = values.length - 1; i >= 0; i--)
            values[i] = valueStack.pull();

        return values;
    }
}
