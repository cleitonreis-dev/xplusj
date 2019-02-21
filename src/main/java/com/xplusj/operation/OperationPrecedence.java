package com.xplusj.operation;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class OperationPrecedence implements Comparable<OperationPrecedence>{
    private static final OperationPrecedence LOWEST = new OperationPrecedence(Integer.MIN_VALUE);
    private static final OperationPrecedence LOW = new OperationPrecedence(0);
    private static final OperationPrecedence MEDIUM = new OperationPrecedence(Integer.MAX_VALUE/3);
    private static final OperationPrecedence HIGH = new OperationPrecedence(MEDIUM.precedence + MEDIUM.precedence);
    private static final OperationPrecedence HIGHEST = new OperationPrecedence(Integer.MAX_VALUE);


    private final int precedence;

    @Override
    public int compareTo(OperationPrecedence o) {
        if(precedence == o.precedence)
            return 0;

        return precedence < o.precedence ? -1 : 1;
    }

    public static OperationPrecedence lowest(){
        return LOWEST;
    }

    public static OperationPrecedence low(){
        return LOW;
    }

    public static OperationPrecedence medium(){
        return MEDIUM;
    }

    public static OperationPrecedence high(){
        return HIGH;
    }

    public static OperationPrecedence highest(){
        return HIGHEST;
    }

    public static OperationPrecedence lowerThan(OperationPrecedence precedence){
        if(precedence.precedence == LOWEST.precedence)
            throw new IllegalArgumentException("Precedence already with LOWEST value");

        return new OperationPrecedence(precedence.precedence - 1);
    }

    public static OperationPrecedence higherThan(OperationPrecedence precedence){
        if(precedence.precedence == HIGHEST.precedence)
            throw new IllegalArgumentException("Precedence already with HIGHEST value");

        return new OperationPrecedence(precedence.precedence + 1);
    }

    public static OperationPrecedence sameAs(OperationPrecedence precedence){
        return precedence;
    }
}
