package com.xplusj.expression;

import com.xplusj.Environment;
import com.xplusj.ExpressionFactory;
import javafx.util.Pair;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.xplusj.Environment.defaultEnv;
import static com.xplusj.ExpressionFactory.defaultFactory;
import static org.junit.Assert.*;

public class FormulaTests {

    private Environment env = defaultEnv().build();
    private ExpressionFactory factory = defaultFactory(env);

    @Test
    public void testPlus(){
        double result = factory.formula("a+a").eval(vars(var("a", 1D)));
        assertEquals(2D, result, 0);
    }

    private static Map<String,Double> vars(Pair<String,Double>...vars){
        Map<String,Double> varsMap = new HashMap<>();
        Stream.of(vars).forEach(pair->varsMap.put(pair.getKey(),pair.getValue()));
        return varsMap;
    }

    private static Pair<String,Double> var(String name, Double value){
        return new Pair<>(name,value);
    }
}