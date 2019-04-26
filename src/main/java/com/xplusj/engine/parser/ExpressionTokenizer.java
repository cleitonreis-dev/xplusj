package com.xplusj.engine.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExpressionTokenizer {
    private static final Map<Character, Function<Integer, Token>> RESERVED_TOKENS = new HashMap<>();
    static {
        RESERVED_TOKENS.put('(', Token::parenthesisOpening);
        RESERVED_TOKENS.put(')', Token::parenthesisClosing);
        RESERVED_TOKENS.put(',', Token::comma);
    }

    private final String expression;
    private final int expressionLength;
    private final ExistingOperator existingOperator;
    private int currentIndex;

    public ExpressionTokenizer(final String expression, final ExistingOperator existingOperator) {
        this.expression = expression;
        this.expressionLength = expression.length();
        this.existingOperator = existingOperator;
    }

    public boolean hasNext(){
        return currentIndex < expressionLength;
    }

    public Token next(){
        if(currentIndex == expressionLength)
            return Token.EOE();

        int startIndex = currentIndex;

        Token token = getKnownToken(startIndex);
        if(token != null)
            return token;

        return getSymbol(startIndex);
    }

    private Token getKnownToken(int startIndex){
        while (true){
            char c = expression.charAt(currentIndex);

            if(isDigit(c)) {
                currentIndex++;

                if(hasNext())
                    continue;
            }

            if (startIndex < currentIndex)
                return Token.number(value(startIndex), startIndex);

            currentIndex++;
            if(existingOperator.exist(c))
                return Token.operator(value(startIndex), startIndex);

            if(RESERVED_TOKENS.containsKey(c))
                return RESERVED_TOKENS.get(c).apply(startIndex);

            break;
        }

        return null;
    }

    private Token getSymbol(int startIndex){
        if(hasNext()){
            char c = expression.charAt(currentIndex);
            while (!(isDigit(c) || existingOperator.exist(c) || RESERVED_TOKENS.containsKey(c))){
                currentIndex++;

                if(hasNext()) {
                    c = expression.charAt(currentIndex);
                    continue;
                }

                break;
            }
        }

        return Token.symbol(value(startIndex),startIndex);
    }

    private String value(int startIndex){
        return expression.substring(startIndex,currentIndex);
    }

    private static boolean isDigit(char c){
        return Character.isDigit(c) || c == '.';
    }

    public static ExpressionTokenizer of(String expression, ExistingOperator existingOperator){
        return new ExpressionTokenizer(expression,existingOperator);
    }

    public interface ExistingOperator{
        boolean exist(char operator);
    }
}
