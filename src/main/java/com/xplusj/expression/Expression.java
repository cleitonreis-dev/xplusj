package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.ExpressionEvaluator;
import com.xplusj.operation.Operation;
import com.xplusj.operation.OperationType;
import com.xplusj.stack.Stack;
import com.xplusj.tokenizer.ExpressionTokenizer;
import com.xplusj.tokenizer.Token;
import com.xplusj.tokenizer.TokenType;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class Expression implements ExpressionEvaluator {

    private final Environment env;
    private final String expression;

    @Override
    public double eval() {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression, env);
        Stack<Operation<?>> opStack = Stack.defaultStack();
        Stack<Double> valStack = Stack.defaultStack();
        OperationValueStackVisitor visitor = new OperationValueStackVisitor(env, valStack);

        int openParenthesis = 0;
        int closedParenthesis = 0;

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.NUMBER){
                valStack.push(Double.valueOf(token.value));
                continue;
            }

            if(token.type == TokenType.CONSTANT){
                valStack.push(env.getConstant(token.value));
                continue;
            }

            if(token.type == TokenType.PARENTHESIS_OPENING){
                openParenthesis++;
                continue;
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                openParenthesis--;
                closedParenthesis = openParenthesis;

                if(!opStack.isEmpty())
                    valStack.push(opStack.pull().accept(visitor));

                continue;
            }

            if(token.type == TokenType.FUNCTION_PARAM_DELIMITER){
                if(opStack.peek().geType() != OperationType.FUNCTION){
                    throw new IllegalArgumentException("invalid character at xxx index");
                }
                continue;
            }

            Operation<?> operator;
            if(token.type == TokenType.BINARY_OPERATOR){
                operator = env.getBinaryOperator(token.value.charAt(0));
            }else if(token.type == TokenType.UNARY_OPERATOR){
                operator = env.getUnaryOperator(token.value.charAt(0));
            }else if(token.type == TokenType.FUNCTION){
                operator = env.getFunction(token.value);
            }else{
                operator = null;
            }

            if(opStack.isEmpty()){
                opStack.push(operator);
            }else{
                if(operator.precedes(opStack.peek())){
                    opStack.push(operator);
                }else{
                    if(closedParenthesis < openParenthesis){
                        opStack.push(operator);
                        closedParenthesis++;
                    }else{
                        valStack.push(opStack.pull().accept(visitor));
                        opStack.push(operator);
                    }
                }
            }
        }

        while (!opStack.isEmpty()){
            valStack.push(opStack.pull().accept(visitor));
        }

        return valStack.pull();
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return 0;
    }
}
