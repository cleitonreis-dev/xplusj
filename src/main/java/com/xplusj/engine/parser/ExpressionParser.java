package com.xplusj.engine.parser;

import com.xplusj.core.GlobalContext;
import com.xplusj.core.operator.Operator;
import com.xplusj.core.operator.OperatorType;

import static java.lang.String.format;

public class ExpressionParser {

    private final GlobalContext context;
    private final ExpressionTokenizer.ExistingOperator existingOperator;

    public ExpressionParser(GlobalContext context) {
        this.context = context;
        existingOperator = op->context.hasBinaryOperator(op) || context.hasUnaryOperator(op);
    }

    public void eval(String expression, InstructionHandler instructionHandler){
        ExpressionTokenizer tokenizer = ExpressionTokenizer.of(expression, existingOperator);

        int stackOperatorCount = 0;
        int openParenthesis = 0;
        int closedParenthesis = 0;

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.NUMBER){
                instructionHandler.pushValue(Double.parseDouble(token.value));
                continue;
            }

            if(token.type == TokenType.SYMBOL && context.hasConstant(token.value)){
                instructionHandler.pushConstant(token.value);
                continue;
            }

            if(token.type == TokenType.PARENTHESIS_OPENING){
                openParenthesis++;
                continue;
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                openParenthesis--;
                closedParenthesis = openParenthesis;

                if(stackOperatorCount > 0) {
                    instructionHandler.callOperator();
                    stackOperatorCount--;
                }

                continue;
            }

            if(token.type == TokenType.COMMA){
                if(instructionHandler.peekOperator().geType() != OperatorType.FUNCTION){
                    throw new IllegalArgumentException(
                            format("invalid character %s at index %s", token.value, token.index));
                }
                continue;
            }

            Operator<?> operator = getOperator(token);

            if(stackOperatorCount == 0){
                instructionHandler.pushOperator(operator);
                stackOperatorCount++;
            }else{
                if(operator.precedes(instructionHandler.peekOperator())){
                    instructionHandler.pushOperator(operator);
                    stackOperatorCount++;
                }else{
                    if(closedParenthesis < openParenthesis){
                        instructionHandler.pushOperator(operator);
                        closedParenthesis++;
                        stackOperatorCount++;
                    }else{
                        instructionHandler.callOperator();
                        instructionHandler.pushOperator(operator);
                    }
                }
            }
        }

        while (stackOperatorCount > 0) {
            instructionHandler.callOperator();
            stackOperatorCount--;
        }
    }

    private Operator<?> getOperator(Token token) {
        if(token.type == TokenType.OPERATOR){
            char c = token.value.charAt(0);

            if(context.hasBinaryOperator(c))
                return context.getBinaryOperator(c);

            return context.getUnaryOperator(c);
        }

        if(token.type == TokenType.SYMBOL && context.hasFunction(token.value))
            return context.getFunction(token.value);

        throw new IllegalStateException(
                format("unrecognized symbol '%s' at index %s ", token.value,token.index));
    }

    public interface InstructionHandler{
        void pushValue(double value);
        void pushConstant(String name);
        void pushOperator(Operator<?> operator);
        void callOperator();
        Operator<?> peekOperator();
    }

}
