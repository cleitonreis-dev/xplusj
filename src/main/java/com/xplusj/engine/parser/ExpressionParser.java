package com.xplusj.engine.parser;

import com.xplusj.core.GlobalContext;
import com.xplusj.core.operator.FunctionOperator;
import com.xplusj.core.operator.Operator;

public class ExpressionParser {

    private final GlobalContext globalContext;
    private final ExpressionTokenizer.OperatorChecker operatorChecker;

    public ExpressionParser(GlobalContext context) {
        this.globalContext= context;
        operatorChecker = op->globalContext.hasBinaryOperator(op) || globalContext.hasUnaryOperator(op);
    }

    public void eval(String expression, InstructionHandler instructionHandler) {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expression,operatorChecker);
        ParseContext parseContext = new ParseContext(ParseContext.Level.EXP,instructionHandler);

        eval(parseContext,tokenizer);

        while(parseContext.stackOperatorCount > 0)
            instructionHandler.callOperator();
    }

    private void eval(ParseContext context, ExpressionTokenizer tokenizer) {

        while (tokenizer.hasNext()){
            Token token = tokenizer.next();

            if(token.type == TokenType.COMMA){
                if(context.isFunc())
                    break;

                throw invalidIdentifier(tokenizer.expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_CLOSING){
                context.lastToken = token;

                if(context.isFunc() || context.isParenthesis())
                    break;

                throw invalidIdentifier(tokenizer.expression, token);
            }

            if(token.type == TokenType.PARENTHESIS_OPENING){
                eval(context.parenthesis(), tokenizer);
                context.lastToken = tokenizer.lastToken;
                continue;
            }

            if(token.type == TokenType.FUNC){
                evalFunc(context, token, tokenizer);
                continue;
            }

            if(token.type == TokenType.NUMBER){
                context.pushValue(token);
                continue;
            }

            if(token.type == TokenType.VAR){
                context.pushVar(token);
                continue;
            }

            if(token.type == TokenType.CONST){
                context.pushConstant(token);
                continue;
            }

            if(token.type == TokenType.OPERATOR){
                Operator<?> operator;
                 boolean isUnary = context.lastToken == null || context.lastToken.type == TokenType.OPERATOR
                         || context.lastToken.type == TokenType.PARENTHESIS_OPENING
                         || context.lastToken.type == TokenType.COMMA;

                if(isUnary)
                    operator = globalContext.getUnaryOperator(token.value.charAt(0));
                else
                    operator = globalContext.getBinaryOperator(token.value.charAt(0));

                if(operator == null){
                    String msg = isUnary ? "Unary operator '%s' not found" : "Binary operator '%s' not found";
                    throw new ExpressionParseException(tokenizer.expression, token.index, msg, token.value);
                }

                if(context.stackOperatorCount == 0 || operator.precedes(context.peekOperator()))
                    context.pushOperator(operator, token);
                else {
                    context.callOperator();
                    context.pushOperator(operator, token);
                }
            }
        }

        while(context.stackOperatorCount > 0)
            context.callOperator();
    }

    private void evalFunc(ParseContext currentContext, Token token, ExpressionTokenizer tokenizer){
        if(!globalContext.hasFunction(token.value))
            throw new ExpressionParseException(tokenizer.expression, token.index, "Function '%s' not found", token.value);

        FunctionOperator function = globalContext.getFunction(token.value);
        currentContext.pushOperator(function,token);

        tokenizer.next();//read next '('
        if(!tokenizer.hasNext())
            throw new ExpressionParseException(tokenizer.expression, tokenizer.expression.length()-1,"Invalid expression");

        int paramsLength = function.getParamsLength();
        for(int i = 0; i < paramsLength; i++) {
            eval(currentContext.func(), tokenizer);

            TokenType lastTokenType = tokenizer.lastToken.type;
            if(lastTokenType != TokenType.COMMA && lastTokenType != TokenType.PARENTHESIS_CLOSING)
                throw new ExpressionParseException(tokenizer.expression, tokenizer.lastToken.index,"Invalid expression");
        }

        currentContext.callOperator();

    }

    private ExpressionParseException invalidIdentifier(String expression, Token token){
        return new ExpressionParseException(expression, token.index,
                "invalid identifier '%s' at index %s", token.value, token.index);
    }

    private static class ParseContext{
        enum Level{PARENTHESIS,FUNC,EXP}
        final Level level;
        final InstructionHandler instructionHandler;
        int stackOperatorCount = 0;
        Token lastToken = null;

        public ParseContext(Level level, InstructionHandler instructionHandler) {
            this.level = level;
            this.instructionHandler = instructionHandler;
        }

        boolean isParenthesis(){
            return level == Level.PARENTHESIS;
        }

        boolean isFunc(){
            return level == Level.FUNC;
        }

        boolean isExp(){
            return level == Level.EXP;
        }

        void pushValue(Token token) {
            instructionHandler.pushValue(Double.parseDouble(token.value));
            lastToken = token;
        }

        void pushVar(Token token) {
            instructionHandler.pushVar(token.value);
            lastToken = token;
        }

        void pushConstant(Token token) {
            instructionHandler.pushConstant(token.value);
            lastToken = token;
        }

        void pushOperator(Operator<?> operator, Token token) {
            instructionHandler.pushOperator(operator);
            stackOperatorCount++;
            lastToken = token;
        }

        void callOperator() {
            instructionHandler.callOperator();
            stackOperatorCount--;
        }

        Operator<?> peekOperator() {
            return instructionHandler.peekOperator();
        }

        ParseContext func(){
            return new ParseContext(Level.FUNC,instructionHandler);
        }

        ParseContext parenthesis(){
            return new ParseContext(Level.PARENTHESIS,instructionHandler);
        }
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
