package com.xplusj.parser;

import com.xplusj.ExpressionOperatorDefinitions;
import com.xplusj.operator.OperatorContext;
import com.xplusj.operator.Operator;
import com.xplusj.operator.function.FunctionOperator;
import com.xplusj.tokenizer.ExpressionTokenizer;
import com.xplusj.tokenizer.Token;
import com.xplusj.tokenizer.TokenType;

public class DefaultExpressionParser implements ExpressionParser {
    private enum ExecContext{PARENTHESIS,FUNC,EXP}

    private final ExpressionOperatorDefinitions globalContext;
    private final ExpressionTokenizer tokenizer;

    private DefaultExpressionParser(ExpressionOperatorDefinitions globalContext, ExpressionTokenizer tokenizer) {
        this.globalContext= globalContext;
        this.tokenizer = tokenizer;
    }

    @Override
    public<ParserResult> ParserResult eval(final String expression, final ExpressionParserProcessor<ParserResult> instructionProcessor) {
        return eval(ExecContext.EXP, tokenizer.tokenize(expression),instructionProcessor);
    }

    private <ParserResult> ParserResult eval(final ExecContext execContext,
                                             final ExpressionTokenizer.Tokenizer tokenizer,
                                             final ExpressionParserProcessor<ParserResult> instructionsProcessor) {
        int stackOperatorCount = 0;
        boolean parenthesisClosed = false;
        Token lastToken = null;
        int currentIndex = tokenizer.getLastToken() == null ? 0 : tokenizer.getLastToken().index;
        String expression = tokenizer.getExpression();

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.COMMA){
                if(execContext == ExecContext.FUNC && lastToken != null)
                    break;

                throw invalidIdentifier(expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                if((execContext == ExecContext.FUNC || execContext == ExecContext.PARENTHESIS) && lastToken != null) {
                    parenthesisClosed = true;
                    break;
                }

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
                stackOperatorCount = defineOperator(instructionsProcessor, stackOperatorCount, lastToken, expression, token);
                lastToken = token;
                continue;
            }

            throw invalidIdentifier(expression, token);
        }

        if(execContext == ExecContext.PARENTHESIS && !parenthesisClosed)
            throw unclosedParenthesis(expression,currentIndex);

        if(lastToken != null && (lastToken.type == TokenType.OPERATOR
                || lastToken.type == TokenType.PARENTHESIS_OPENING
                || lastToken.type == TokenType.COMMA))
            throw unexpectedEnd(expression,lastToken.index);

        while(stackOperatorCount > 0) {
            instructionsProcessor.callLastOperatorAndAddResult();
            stackOperatorCount--;
        }

        return instructionsProcessor.getResult();
    }

    private int defineOperator(ExpressionParserProcessor instructionsProcessor, int currentStackCount, Token lastToken, String expression, Token token) {
        Operator<? extends OperatorContext> operator;
        boolean isUnary = lastToken == null || lastToken.type == TokenType.OPERATOR
                || lastToken.type == TokenType.PARENTHESIS_OPENING
                || lastToken.type == TokenType.COMMA;

        if(isUnary)
            operator = globalContext.getUnaryOperator(token.value);
        else
            operator = globalContext.getBinaryOperator(token.value);

        if(operator == null){
            String msg = (isUnary ? "Unary" : "Binary") + " operator '%s' not found";
            throw new ExpressionParseException(expression, token.index, msg, token.value);
        }

        if(currentStackCount == 0 || operator.precedes(instructionsProcessor.getLastOperator())) {
            instructionsProcessor.addOperator(operator);
            currentStackCount++;
        }else {
            instructionsProcessor.callLastOperatorAndAddResult();
            instructionsProcessor.addOperator(operator);
        }

        return currentStackCount;
    }

    private void evalFunc(Token token, final ExpressionTokenizer.Tokenizer tokenizer, ExpressionParserProcessor<?> instructionsProcessor){
        if(!globalContext.hasFunction(token.value))
            throw new ExpressionParseException(tokenizer.getExpression(), token.index, "Function '%s' not found", token.value);

        FunctionOperator function = globalContext.getFunction(token.value);
        instructionsProcessor.addOperator(function);

        tokenizer.next();//read next '('
        if(!tokenizer.hasNext())
            throw unclosedParenthesis(tokenizer.getExpression(), tokenizer.getLastToken().index);

        int readParams = 0;
        while (true){
            eval(ExecContext.FUNC, tokenizer, instructionsProcessor);
            TokenType lastTokenType = tokenizer.getLastToken().type;
            readParams++;

            if(lastTokenType != TokenType.COMMA && lastTokenType != TokenType.PARENTHESIS_CLOSING)
                throw new ExpressionParseException(tokenizer.getExpression(),
                        tokenizer.getLastToken().index,"Function not closed properly");

            if(lastTokenType == TokenType.PARENTHESIS_CLOSING)
                break;
        }

        int totalFuncParams = function.getParamsLength();
        boolean isVarArgs = function.isVarArgs();

        if(readParams != totalFuncParams && !isVarArgs)
            throw new ExpressionParseException(tokenizer.getExpression(),
                    token.index,"Function requires %s parameters", totalFuncParams);

        if(readParams < totalFuncParams - 1 && isVarArgs)
            throw new ExpressionParseException(tokenizer.getExpression(),
                    token.index,"Function requires at least %s parameters", totalFuncParams - 1);

        instructionsProcessor.callLastOperatorAndAddResult(readParams);
    }

    private static ExpressionParseException invalidIdentifier(String expression, Token token){
        return new ExpressionParseException(expression, token.index,
                "invalid identifier '%s' at index %s", token.value, token.index);
    }

    private static ExpressionParseException unexpectedEnd(String expression, int index){
        return new ExpressionParseException(expression,index,"Unexpected end of expression");
    }

    private static ExpressionParseException unclosedParenthesis(String expression, int index){
        return new ExpressionParseException(expression,index,"Unclosed parenthesis at index %s",index);
    }

    public static DefaultExpressionParser create(ExpressionOperatorDefinitions context, ExpressionTokenizer tokenizer){
        return new DefaultExpressionParser(context,tokenizer);
    }
}
