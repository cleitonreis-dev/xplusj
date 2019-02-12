package com.xplusj;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class OperationPrecedence implements Comparable<OperationPrecedence>{
    private static final OperationPrecedence LOW = new OperationPrecedence(0);
    private static final OperationPrecedence MEDIUM = new OperationPrecedence(Integer.MAX_VALUE/3);
    private static final OperationPrecedence HIGH = new OperationPrecedence(MEDIUM.precedence + MEDIUM.precedence);
    private static final OperationPrecedence HIGHER = new OperationPrecedence(Integer.MAX_VALUE);


    private final int precedence;

    @Override
    public int compareTo(OperationPrecedence o) {
        if(precedence == o.precedence)
            return 0;

        return precedence < o.precedence ? -1 : 1;
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

    public static OperationPrecedence higher(){
        return HIGHER;
    }

    public static OperationPrecedence lowerThan(OperationPrecedence precedence){
        if(precedence.precedence == Integer.MIN_VALUE)
            throw new IllegalArgumentException("Precedence with min value set");

        return new OperationPrecedence(precedence.precedence - 1);
    }

    public static OperationPrecedence higherThan(OperationPrecedence precedence){
        if(precedence.precedence == Integer.MAX_VALUE)
            throw new IllegalArgumentException("Precedence with max value set");

        return new OperationPrecedence(precedence.precedence + 1);
    }

    public static OperationPrecedence sameAs(OperationPrecedence precedence){
        return precedence;
    }
}
