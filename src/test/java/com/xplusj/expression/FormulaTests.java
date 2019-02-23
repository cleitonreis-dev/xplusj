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
        double result = factory.formula("a+a").eval(vars(var("a", 1)));
        assertEquals(2D, result, 0);
    }

    @Test
    public void testPlusAndMinus(){
        double result = factory.formula("a+b-a").eval(vars(var("a", 3),var("b", 1D)));
        assertEquals(1D, result, 0);
    }

    @Test
    public void testCallingFunction(){
        double result = factory.formula("max(3,x)").eval(vars(var("x",3)));
        assertEquals(3D, result, 0);
    }

    @Test
    public void testMultiplyWithFunctionCall(){
        double result = factory.formula("ab*max(3,ab)").eval(vars(var("ab",2)));
        assertEquals(6D, result, 0);
    }

    private static Map<String,Double> vars(Pair<String,Double>...vars){
        Map<String,Double> varsMap = new HashMap<>();
        Stream.of(vars).forEach(pair->varsMap.put(pair.getKey(),pair.getValue()));
        return varsMap;
    }

    private static Pair<String,Double> var(String name, double value){
        return new Pair<>(name,value);
    }
}