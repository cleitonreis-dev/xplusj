package com.xplusj.operator;

public class Constant {
    private final String name;
    private final double value;

    private Constant(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public static Constant newConst(String name, double value){
        return new Constant(name, value);
    }
}
