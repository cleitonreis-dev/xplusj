package com.xplusj.expression;

import com.xplusj.ExpressionContext;
import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.VariableContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorContext;
import com.xplusj.operator.OperatorDefinition;
import com.xplusj.operator.OperatorType;
import com.xplusj.parser.ExpressionParserProcessor;

public class TwoStackBasedProcessor implements ExpressionParserProcessor<Double> {

    private final ExpressionContext expressionContext;
    private final ExpressionOperatorDefinitions operatorDefinitions;
    private final VariableContext variableContext;
    private final Stack<Double> valueStack;
    private final Stack<OperatorDefinition<? extends OperatorContext>> opStack;

    protected TwoStackBasedProcessor(ExpressionContext expressionContext,
                                     VariableContext variableContext,
                                     Stack<Double> valueStack,
                                     Stack<OperatorDefinition<?>> opStack) {
        this.expressionContext = expressionContext;
        this.operatorDefinitions = expressionContext.getDefinitions();
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
        if(!operatorDefinitions.hasConstant(name))
            throw new ExpressionException("Constant '"+ name +"' not found");

        valueStack.push(operatorDefinitions.getConstant(name).getValue());
    }

    @Override
    public void addOperator(OperatorDefinition<? extends OperatorContext> operator) {
        opStack.push(operator);
    }

    @Override
    public void callLastOperatorAndAddResult() {
        Operator<? extends OperatorContext> operator = getOperator();
        double value = operator.execute(getParams(operator.getDefinition().getParamsLength()));
        valueStack.push(value);
    }

    @Override
    public void callLastOperatorAndAddResult(int totalOfParamsToRead) {
        Operator<? extends OperatorContext> operator = getOperator();
        double value = operator.execute(getParams(totalOfParamsToRead));
        valueStack.push(value);
    }

    @Override
    public OperatorDefinition<?> getLastOperator() {
        return opStack.peek();
    }

    @Override
    public Double getResult() {
        return valueStack.peek();
    }

    private double[] getParams(int totalOfParamsToRead){
        double[] values = new double[totalOfParamsToRead];

        for(int i = values.length - 1; i >= 0; i--)
            values[i] = valueStack.pull();

        return values;
    }

    private Operator<? extends OperatorContext> getOperator(){
        OperatorDefinition<? extends OperatorContext> operator = opStack.pull();

        if(operator.getType() == OperatorType.BINARY)
            return expressionContext.getBinaryOperator(operator.getIdentifier());

        if(operator.getType() == OperatorType.FUNCTION)
            return expressionContext.getFunction(operator.getIdentifier());

        if(operator.getType() == OperatorType.UNARY)
            return expressionContext.getUnaryOperator(operator.getIdentifier());

        throw new ExpressionException(String.format("Operator type %s not supported", operator.getType()));
    }

    public static TwoStackBasedProcessor create(ExpressionContext context, VariableContext variableContext, Stack<Double> valueStack, Stack<OperatorDefinition<?>> opStack){
        return new TwoStackBasedProcessor(context,variableContext,valueStack,opStack);
    }

    public static TwoStackBasedProcessor create(ExpressionContext context, VariableContext variableContext){
        return new TwoStackBasedProcessor(context,variableContext,Stack.instance(),Stack.instance());
    }
}
