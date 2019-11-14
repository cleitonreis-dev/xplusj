package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.operator.*;
import com.xplusj.parser.ExpressionParserProcessor;

public class TwoStackBasedProcessor implements ExpressionParserProcessor<Double> {

    private final GlobalContext globalContext;
    private final VariableContext variableContext;
    private final Stack<Double> valueStack;
    private final Stack<Operator<? extends OperatorContext>> opStack;

    private TwoStackBasedProcessor(Environment env, VariableContext variableContext, Stack<Double> valueStack, Stack<Operator<?>> opStack) {
        this.globalContext = env.getContext();
        this.variableContext = variableContext;
        this.valueStack = valueStack;
        this.opStack = opStack;
    }

    @Override
    public void addValue(double value) {
        valueStack.push(value);
    }

    @Override
    public void addVar(String value) {
        if(!variableContext.contains(value))
            throw new ExpressionException("Variable '"+ value +"' not found");

        valueStack.push(variableContext.value(value));
    }

    @Override
    public void addConstant(String name) {
        if(!globalContext.hasConstant(name))
            throw new ExpressionException("Constant '"+ name +"' not found");

        valueStack.push(globalContext.getConstant(name));
    }

    @Override
    public void addOperator(Operator<? extends OperatorContext> operator) {
        opStack.push(operator);
    }

    @Override
    public void callLastOperatorAndAddResult() {
        Operator<? extends OperatorContext> operator = opStack.pull();
        double value = operator.execute(getParams(operator.getDefinition()));
        valueStack.push(value);
    }

    @Override
    public Operator<?> getLastOperator() {
        return opStack.peek();
    }

    @Override
    public Double getResult() {
        return valueStack.peek();
    }

    private double[] getParams(OperatorDefinition<?> operator){
        double[] values = new double[operator.getParamsLength()];

        for(int i = values.length - 1; i >= 0; i--)
            values[i] = valueStack.pull();

        return values;
    }

    static TwoStackBasedProcessor create(Environment env, VariableContext variableContext){
        return new TwoStackBasedProcessor(env,variableContext,Stack.instance(),Stack.instance());
    }

    static TwoStackBasedProcessor create(Environment env, VariableContext variableContext, Stack<Double> valueStack, Stack<Operator<?>> opStack){
        return new TwoStackBasedProcessor(env,variableContext,valueStack,opStack);
    }
}
