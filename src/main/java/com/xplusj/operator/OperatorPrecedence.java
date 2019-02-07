package com.xplusj.operator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OperatorPrecedence implements Comparable<OperatorPrecedence>{
    private static final OperatorPrecedence LOW = new OperatorPrecedence(0);
    private static final OperatorPrecedence MEDIUM = new OperatorPrecedence(Integer.MAX_VALUE/3);
    private static final OperatorPrecedence HIGH = new OperatorPrecedence(Integer.MAX_VALUE/3 + Integer.MAX_VALUE/3);
    private static final OperatorPrecedence HIGHER = new OperatorPrecedence(Integer.MAX_VALUE);


    private final int precedence;

    @Override
    public int compareTo(OperatorPrecedence o) {
        if(precedence == o.precedence)
            return 0;

        return precedence < o.precedence ? -1 : 1;
    }

    public static OperatorPrecedence low(){
        return LOW;
    }

    public static OperatorPrecedence medium(){
        return MEDIUM;
    }

    public static OperatorPrecedence high(){
        return HIGH;
    }

    public static OperatorPrecedence higher(){
        return HIGHER;
    }

    public static OperatorPrecedence lowerThan(OperatorPrecedence precedence){
        return new OperatorPrecedence(precedence.precedence - 1);
    }

    public static OperatorPrecedence higherThan(OperatorPrecedence precedence){
        return new OperatorPrecedence(precedence.precedence + 1);
    }

    public static OperatorPrecedence sameAs(OperatorPrecedence precedence){
        return new OperatorPrecedence(precedence.precedence);
    }
}
