package com.xplusj.engine.parser;

import com.xplusj.core.GlobalContext;
import com.xplusj.core.operator.Operator;
import com.xplusj.core.operator.OperatorType;

import static java.lang.String.format;

public class ExpressionParser {

    private final GlobalContext context;
    private final ExpressionTokenizer.OperatorChecker operatorChecker;

    public ExpressionParser(GlobalContext context) {
        this.context = context;
        operatorChecker = op->context.hasBinaryOperator(op) || context.hasUnaryOperator(op);
    }

    public void eval(String expression, InstructionHandler instructionHandler){
        ExpressionTokenizer tokenizer = ExpressionTokenizer.of(expression, operatorChecker);

        int stackOperatorCount = 0;
        int openParenthesis = 0;
        int closedParenthesis = 0;
        int funcParamLevel = 0;

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.NUMBER){
                instructionHandler.pushValue(Double.parseDouble(token.value));
                continue;
            }

            if(token.type == TokenType.CONST){
                if(!context.hasConstant(token.value)){
                    throw new ExpressionParseException(
                        format("constant '%s' not found", token.value), expression, token.index);
                }

                instructionHandler.pushConstant(token.value);
                continue;
            }

            if(token.type == TokenType.VAR){
                instructionHandler.pushVar(token.value);
                continue;
            }

            if(token.type == TokenType.PARENTHESIS_OPENING){
                openParenthesis++;
                continue;
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                if(funcParamLevel > 0 && openParenthesis == funcParamLevel && instructionHandler.peekOperator().geType() != OperatorType.FUNCTION) {
                    instructionHandler.callOperator();
                    funcParamLevel--;
                    stackOperatorCount--;
                }

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
                    throw new ExpressionParseException(
                            format("invalid character ',' at index %s", token.index),
                            expression, token.index);
                }
                continue;
            }

            if(token.type == TokenType.FUNC)
                funcParamLevel++;

            Operator<?> operator = getOperator(token,expression);

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

    private Operator<?> getOperator(Token token, String expression) {
        if(token.type == TokenType.OPERATOR){
            char c = token.value.charAt(0);

            if(context.hasBinaryOperator(c))
                return context.getBinaryOperator(c);

            return context.getUnaryOperator(c);
        }

        if(token.type == TokenType.FUNC){
            if(!context.hasFunction(token.value)){
                throw new ExpressionParseException(
                    format("function '%s' not found ", token.value), expression,token.index);
            }

            return context.getFunction(token.value);
        }

        throw new IllegalStateException(
                format("unrecognized symbol '%s' at index %s ", token.value,token.index));
    }

    public interface InstructionHandler{
        void pushValue(double value);
        void pushVar(String value);
        void pushConstant(String name);
        void pushOperator(Operator<?> operator);
        void callOperator();
        Operator<?> peekOperator();
    }

}
