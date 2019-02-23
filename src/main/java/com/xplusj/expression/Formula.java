package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.FormulaExpression;
import com.xplusj.expression.parser.Instruction;
import com.xplusj.expression.parser.ParserContext;
import com.xplusj.operation.Operation;
import com.xplusj.operation.OperationType;
import com.xplusj.stack.Stack;
import com.xplusj.tokenizer.ExpressionTokenizer;
import com.xplusj.tokenizer.Token;
import com.xplusj.tokenizer.TokenType;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xplusj.expression.parser.Instruction.*;

@RequiredArgsConstructor
public class Formula implements FormulaExpression {
    private final Object LOCK = new Object();

    private final Environment env;
    private final String expression;
    private List<Instruction> instructions;

    @Override
    public double eval(Map<String, Double> variables) {
        if(instructions == null){
            loadInstructions();
        }

        Stack<Operation<?>> opStack = Stack.defaultStack();
        Stack<Double> valStack = Stack.defaultStack();
        OperationValueStackVisitor visitor = new OperationValueStackVisitor(env, valStack);
        ParserContext pc = new ParserContext(opStack,valStack,env,variables,visitor);

        instructions.forEach(inst->inst.execute(pc));

        return valStack.pull();
    }

    private void loadInstructions() {
        synchronized (LOCK){
            if(instructions != null)
                return;

            instructions = new ArrayList<>();
            ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression, env);
            Stack<Operation<?>> opStack = Stack.defaultStack();

            int openParenthesis = 0;
            int closedParenthesis = 0;

            while (tokenizer.hasNext()){
                Token token = tokenizer.next();

                if(token.type == TokenType.NUMBER){
                    instructions.add(pushValue(Double.valueOf(token.value)));
                    continue;
                }

                if(token.type == TokenType.CONSTANT){
                    instructions.add(pushConstant(token.value));
                    continue;
                }

                if(token.type == TokenType.VARIABLE){
                    instructions.add(pushVar(token.value));
                    continue;
                }

                if(token.type == TokenType.PARENTHESIS_OPENING){
                    openParenthesis++;
                    continue;
                }

                if(token.type == TokenType.PARENTHESIS_CLOSING){
                    openParenthesis--;
                    closedParenthesis = openParenthesis;

                    if(!opStack.isEmpty()) {
                        instructions.add(execOperation());
                        opStack.pull();
                    }

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
                    instructions.add(pushOperation(operator));
                }else{
                    if(operator.precedes(opStack.peek())){
                        opStack.push(operator);
                        instructions.add(pushOperation(operator));
                    }else{
                        if(closedParenthesis < openParenthesis){
                            instructions.add(pushOperation(operator));
                            closedParenthesis++;
                        }else{
                            instructions.add(execOperation());
                            opStack.pull();
                            opStack.push(operator);
                            instructions.add(pushOperation(operator));
                        }
                    }
                }
            }

            while (!opStack.isEmpty()) {
                instructions.add(execOperation());
                opStack.pull();
            }
        }
    }
}
