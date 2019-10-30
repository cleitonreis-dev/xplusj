package com.xplusj.parser;

import com.xplusj.GlobalContext;
import com.xplusj.operator.FunctionOperator;
import com.xplusj.operator.Operator;
import com.xplusj.operator.OperatorContext;
import com.xplusj.tokenizer.ExpressionTokenizer;
import com.xplusj.tokenizer.Token;
import com.xplusj.tokenizer.TokenType;

public class DefaultExpressionParser implements ExpressionParser {
    private enum ExecContext{PARENTHESIS,FUNC,EXP}

    private final GlobalContext globalContext;
    private final ExpressionTokenizer tokenizer;

    private DefaultExpressionParser(GlobalContext globalContext, ExpressionTokenizer tokenizer) {
        this.globalContext= globalContext;
        this.tokenizer = tokenizer;
    }

    @Override
    public void eval(final String expression, final ExpressionParserProcessor instructionHandler) {
        eval(ExecContext.EXP, tokenizer.tokenize(expression),instructionHandler);
    }

    private void eval(final ExecContext execContext, final ExpressionTokenizer.Tokenizer tokenizer, final ExpressionParserProcessor instructionsProcessor) {
        int stackOperatorCount = 0;
        Token lastToken = null;
        String expression = tokenizer.getExpression();

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.COMMA){
                if(execContext == ExecContext.FUNC && lastToken != null)
                    break;

                throw invalidIdentifier(expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                if((execContext == ExecContext.FUNC || execContext == ExecContext.PARENTHESIS) && lastToken != null)
                    break;

                throw invalidIdentifier(expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_OPENING){
                eval(ExecContext.PARENTHESIS, tokenizer, instructionsProcessor);
                lastToken = tokenizer.getLastToken();
                continue;
            }

            if(token.type == TokenType.FUNC){
                evalFunc(token, tokenizer, instructionsProcessor);
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.NUMBER){
                instructionsProcessor.addValue(Double.parseDouble(token.value));
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.VAR){
                instructionsProcessor.addVar(token.value);
                lastToken = token;
                continue;
            }

            if(token.type == TokenType.CONST){
                instructionsProcessor.addConstant(token.value);
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
                    throw new ExpressionParseException(expression, token.index, msg, token.value);
                }

                if(stackOperatorCount == 0 || operator.precedes(instructionsProcessor.getLastOperator())) {
                    instructionsProcessor.addOperator(operator);
                    stackOperatorCount++;
                }else {
                    instructionsProcessor.callLastOperatorAndAddResult();
                    instructionsProcessor.addOperator(operator);
                }

                lastToken = token;
                continue;
            }

            throw invalidIdentifier(expression, token);
        }

        if(lastToken != null && (lastToken.type == TokenType.OPERATOR
                || lastToken.type == TokenType.PARENTHESIS_OPENING
                || lastToken.type == TokenType.COMMA))
            throw new ExpressionParseException(expression,lastToken.index,"Unexpected end of expression");

        while(stackOperatorCount > 0) {
            instructionsProcessor.callLastOperatorAndAddResult();
            stackOperatorCount--;
        }
    }

    private void evalFunc(Token token, final ExpressionTokenizer.Tokenizer tokenizer, ExpressionParserProcessor instructionsProcessor){
        if(!globalContext.hasFunction(token.value))
            throw new ExpressionParseException(tokenizer.getExpression(), token.index, "Function '%s' not found", token.value);

        FunctionOperator function = globalContext.getFunction(token.value);
        instructionsProcessor.addOperator(function);

        tokenizer.next();//read next '('
        if(!tokenizer.hasNext())
            throw new ExpressionParseException(tokenizer.getExpression(), tokenizer.getExpression().length()-1,"Invalid expression");

        int paramsLength = function.getParamsLength();
        for(int i = 0; i < paramsLength; i++) {
            eval(ExecContext.FUNC, tokenizer, instructionsProcessor);

            TokenType lastTokenType = tokenizer.getLastToken().type;

            if((i < (paramsLength - 1) && lastTokenType != TokenType.COMMA)
                || (i == (paramsLength - 1) && lastTokenType != TokenType.PARENTHESIS_CLOSING))
                throw new ExpressionParseException(tokenizer.getExpression(),
                        tokenizer.getLastToken().index,"Invalid expression");
        }

        instructionsProcessor.callLastOperatorAndAddResult();
    }

    private ExpressionParseException invalidIdentifier(String expression, Token token){
        return new ExpressionParseException(expression, token.index,
                "invalid identifier '%s' at index %s", token.value, token.index);
    }

    public static DefaultExpressionParser create(GlobalContext context, ExpressionTokenizer tokenizer){
        return new DefaultExpressionParser(context,tokenizer);
    }
}
