package com.xplusj.operator;

public class Precedence implements Comparable<Precedence>{
    private static final Precedence LOWEST = new Precedence(Integer.MIN_VALUE);
    private static final Precedence LOW = new Precedence(0);
    private static final Precedence MEDIUM = new Precedence(Integer.MAX_VALUE/3);
    private static final Precedence HIGH = new Precedence(MEDIUM.precedence + MEDIUM.precedence);
    private static final Precedence HIGHEST = new Precedence(Integer.MAX_VALUE);

    private final int precedence;

    public Precedence(int precedence) {
        this.precedence = precedence;
    }

    @Override
    public int compareTo(Precedence o) {
        if(precedence == o.precedence)
            return 0;

        return precedence < o.precedence ? -1 : 1;
    }

    @Override
    public String toString() {
        return "Precedence{" +
                "precedence=" + precedence +
                '}';
    }

    public static Precedence lowest(){
        return LOWEST;
    }

    public static Precedence low(){
        return LOW;
    }

    public static Precedence medium(){
        return MEDIUM;
    }

    public static Precedence high(){
        return HIGH;
    }

    public static Precedence highest(){
        return HIGHEST;
    }

    public static Precedence lowerThan(Precedence precedence){
        if(precedence.precedence == LOWEST.precedence)
            throw new IllegalArgumentException("Precedence already with LOWEST value");

        return new Precedence(precedence.precedence - 1);
    }

    public static Precedence higherThan(Precedence precedence){
        if(precedence.precedence == HIGHEST.precedence)
            throw new IllegalArgumentException("Precedence already with HIGHEST value");

        return new Precedence(precedence.precedence + 1);
    }

    public static Precedence sameAs(Precedence precedence){
        return precedence;
    }
}
