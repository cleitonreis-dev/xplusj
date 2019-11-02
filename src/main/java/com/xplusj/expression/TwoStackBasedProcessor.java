package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.GlobalContext;
import com.xplusj.VariableContext;
import com.xplusj.operator.*;
import com.xplusj.parser.ExpressionParserProcessor;

public class TwoStackBasedProcessor implements ExpressionParserProcessor {

    private final Environment env;
    private final GlobalContext globalContext;
    private final VariableContext variableContext;
    private final Stack<Double> valueStack;
    private final Stack<Operator<?>> opStack;

    TwoStackBasedProcessor(Environment env, VariableContext variableContext, Stack<Double> valueStack, Stack<Operator<?>> opStack) {
        this.env = env;
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
        if(variableContext.contains(value)) {
            valueStack.push(variableContext.value(value));
            return;
        }

        //TODO create specialized exception
        throw new IllegalStateException("Variable '"+ value +"' not found");
    }

    @Override
    public void addConstant(String name) {
        if(globalContext.hasConstant(name)) {
            valueStack.push(globalContext.getConstant(name));
            return;
        }

        //TODO create specialized exception
        throw new IllegalStateException("Constant '"+ name +"' not found");
    }

    @Override
    public void addOperator(Operator<? extends OperatorContext> operator) {
        opStack.push(operator);
    }

    @Override
    public void callLastOperatorAndAddResult() {
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

    static TwoStackBasedProcessor create(Environment env, VariableContext variableContext, Stack<Double> valueStack, Stack<Operator<?>> opStack){
        return new TwoStackBasedProcessor(env,variableContext,valueStack,opStack);
    }

    private <Context extends OperatorContext> Context getContext(Operator<?> operator){
        OperatorContext context;
        if(operator.getType() == OperatorType.UNARY)
            context = new UnaryOperatorContext(env,getParams(operator)[0]);
        else if(operator.getType() == OperatorType.BINARY)
            context = new BinaryOperatorContext(env,getParams(operator));
        else if(operator.getType() == OperatorType.FUNCTION)
            context = new FunctionOperatorContext((FunctionOperator)operator,env,getParams(operator));
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
