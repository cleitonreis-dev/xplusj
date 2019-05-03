package com.xplusj.engine.parser;

import com.xplusj.core.GlobalContext;
import com.xplusj.core.operator.FunctionOperator;
import com.xplusj.core.operator.Operator;
import com.xplusj.core.operator.OperatorContext;

public class ExpressionParser {
    private enum ExecContext{PARENTHESIS,FUNC,EXP}

    private final GlobalContext globalContext;
    private final ExpressionTokenizer.OperatorChecker operatorChecker;

    public ExpressionParser(GlobalContext context) {
        this.globalContext= context;
        operatorChecker = op->globalContext.hasBinaryOperator(op) || globalContext.hasUnaryOperator(op);
    }

    public void eval(final String expression, final ExpressionInstructionsProcessor instructionHandler) {
        eval(ExecContext.EXP, new ExpressionTokenizer(expression,operatorChecker),instructionHandler);
    }

    private void eval(final ExecContext execContext, final ExpressionTokenizer tokenizer, final ExpressionInstructionsProcessor instructionsProcessor) {
        int stackOperatorCount = 0;
        Token lastToken = null;

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.COMMA){
                if(execContext == ExecContext.FUNC && lastToken != null)
                    break;

                throw invalidIdentifier(tokenizer.expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                if((execContext == ExecContext.FUNC || execContext == ExecContext.PARENTHESIS) && lastToken != null)
                    break;

                throw invalidIdentifier(tokenizer.expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_OPENING){
                eval(ExecContext.PARENTHESIS, tokenizer, instructionsProcessor);
                lastToken = tokenizer.lastToken;
                continue;
            }

            if(token.type == TokenType.FUNC){
                evalFunc(token, tokenizer, instructionsProcessor);
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.NUMBER){
                instructionsProcessor.pushValue(Double.parseDouble(token.value));
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.VAR){
                instructionsProcessor.pushVar(token.value);
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.CONST){
                instructionsProcessor.pushConstant(token.value);
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.OPERATOR){
                Operator<? extends OperatorContext> operator;
                 boolean isUnary = lastToken == null || lastToken.type == TokenType.OPERATOR
                         || lastToken.type == TokenType.PARENTHESIS_OPENING
                         || lastToken.type == TokenType.COMMA;

                if(isUnary)
                    operator = globalContext.getUnaryOperator(token.value.charAt(0));
                else
                    operator = globalContext.getBinaryOperator(token.value.charAt(0));

                if(operator == null){
                    String msg = isUnary ? "Unary operator '%s' not found" : "Binary operator '%s' not found";
                    throw new ExpressionParseException(tokenizer.expression, token.index, msg, token.value);
                }

                if(stackOperatorCount == 0 || operator.precedes(instructionsProcessor.getLastOperator())) {
                    instructionsProcessor.pushOperator(operator);
                    stackOperatorCount++;
                }else {
                    instructionsProcessor.callLastOperatorAndPushResult();
                    instructionsProcessor.pushOperator(operator);
                }

                lastToken = token;
                continue;
            }

            throw invalidIdentifier(tokenizer.expression, token);
        }

        if(lastToken != null && (lastToken.type == TokenType.OPERATOR
                || lastToken.type == TokenType.PARENTHESIS_OPENING
                || lastToken.type == TokenType.COMMA))
            throw new ExpressionParseException(tokenizer.expression,lastToken.index,"Unexpected end of expression");

        while(stackOperatorCount > 0) {
            instructionsProcessor.callLastOperatorAndPushResult();
            stackOperatorCount--;
        }
    }

    private void evalFunc(Token token, ExpressionTokenizer tokenizer, ExpressionInstructionsProcessor instructionsProcessor){
        if(!globalContext.hasFunction(token.value))
            throw new ExpressionParseException(tokenizer.expression, token.index, "Function '%s' not found", token.value);

        FunctionOperator function = globalContext.getFunction(token.value);
        instructionsProcessor.pushOperator(function);

        tokenizer.next();//read next '('
        if(!tokenizer.hasNext())
            throw new ExpressionParseException(tokenizer.expression, tokenizer.expression.length()-1,"Invalid expression");

        int paramsLength = function.getParamsLength();
        for(int i = 0; i < paramsLength; i++) {
            eval(ExecContext.FUNC, tokenizer, instructionsProcessor);

            TokenType lastTokenType = tokenizer.lastToken.type;

            if((i < (paramsLength - 1) && lastTokenType != TokenType.COMMA)
                || (i == (paramsLength - 1) && lastTokenType != TokenType.PARENTHESIS_CLOSING))
                throw new ExpressionParseException(tokenizer.expression, tokenizer.lastToken.index,"Invalid expression");
        }

        instructionsProcessor.callLastOperatorAndPushResult();
    }

    private ExpressionParseException invalidIdentifier(String expression, Token token){
        return new ExpressionParseException(expression, token.index,
                "invalid identifier '%s' at index %s", token.value, token.index);
    }
}
