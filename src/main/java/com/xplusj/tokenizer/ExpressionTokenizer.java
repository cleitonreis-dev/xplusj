package com.xplusj.tokenizer;

import com.xplusj.Environment;

public class ExpressionTokenizer {

    private final String expression;
    private final int expressionLength;
    private final Environment environment;
    private int currentIndex;

    public ExpressionTokenizer(final String expression, final Environment environment) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.environment = environment;
    }

    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

    public Token next(){
        if(currentIndex >= expressionLength)
            return Token.EOE();

        int startIndex = currentIndex;

        while(currentIndex < expressionLength && Character.isDigit(expression.charAt(currentIndex))){
            ++currentIndex;
        }

        if(startIndex < currentIndex)
            return Token.number(expression.substring(startIndex, currentIndex));

        char c = expression.charAt(currentIndex++);

        if('(' == c)
            return Token.parenthesisOpening();

        if(')' == c)
            return Token.parenthesisClosing();

        if(',' == c)
            return Token.getFunctionParamDelimiter();

        if(environment.hasOperator(c))
            return Token.operator(expression.substring(startIndex, currentIndex));

        while(currentIndex < expressionLength && 'a'<= c && c <= 'z'){
            c = expression.charAt(++currentIndex);
        }

        if(startIndex < currentIndex){
            String funcName = expression.substring(startIndex, currentIndex);
            if(environment.hasFunction(funcName)){
                return Token.function(funcName);
            }
        }

        return Token.variable(expression.substring(startIndex, currentIndex));
    }
}
