package com.xplusj.operation;

import java.util.HashMap;
import java.util.Map;

public class ConstantMapBuilder {
    private Map<String,Double> constants = new HashMap<>();

    public ConstantMapBuilder add(String name, Double value){
        constants.put(name,value);
        return this;
    }

    public Map<String,Double> build(){
        return constants;
    }

    public static ConstantMapBuilder builder(){
        return new ConstantMapBuilder();
    }
}
