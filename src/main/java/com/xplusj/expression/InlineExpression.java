package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.stack.Stack;
import com.xplusj.tokenizer.ExpressionTokenizer;
import com.xplusj.tokenizer.Token;
import com.xplusj.tokenizer.TokenType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InlineExpression {

    private final Environment env;

    public double eval(final String expression){
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression, env);
        Stack<StackBasedExecutor> opStack = Stack.defaultStack();
        Stack<Double> valStack = Stack.defaultStack();

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.NUMBER){
                valStack.push(Double.valueOf(token.value));
                continue;
            }

            if(token.type == TokenType.BINARY_OPERATOR){
                StackBasedExecutor operator = new BinaryOperatorStackBasedExecutor(
                    env.getBinaryOperator(token.value.charAt(0)));

                if(opStack.isEmpty()){
                    opStack.push(operator);
                }else{
                    if(operator.precedes(opStack.peek())){
                        opStack.push(operator);
                    }else{
                        opStack.pull().execute(valStack);
                    }
                }
            }
        }

        while (!opStack.isEmpty()){
            opStack.pull().execute(valStack);
        }

        return valStack.pull();
    }
}
