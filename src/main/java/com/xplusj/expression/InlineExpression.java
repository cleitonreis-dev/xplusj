package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.stack.Stack;
import com.xplusj.tokenizer.ExpressionTokenizer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InlineExpression {

    private final Environment env;

    public double eval(final String expression){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression, env);
        Stack<String> opStack = Stack.defaultStack();
        Stack<Double> valStack = Stack.defaultStack();

        while (tokenizer.hasNext()){

        }

        while (!opStack.isEmpty()){

        }

        return valStack.pull();
    }
}
