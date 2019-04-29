package com.xplusj.engine.parser;

public class ExpressionParseException extends RuntimeException {
    private final String expression;
    private final int errorIndex;

    public ExpressionParseException(String message, String expression, int errorIndex) {
        super(message);
        this.expression = expression;
        this.errorIndex = errorIndex;
    }

    @Override
    public String getMessage() {
        return String.format("%s\n%s\n%s",
                super.getMessage(),
                expression,
                getIndexPointer()
        );
    }

    private String getIndexPointer() {
        StringBuilder sb = new StringBuilder(expression.length());

        for(int i = 0; i < expression.length(); i++)
            sb.append(i == errorIndex ? '^' : ' ');

        return sb.toString();
    }
}
