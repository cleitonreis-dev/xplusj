package com.xplusj.operator;

import org.junit.Test;

import java.util.Objects;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OperatorExecutorDefinitionTest {

    @Test
    public void testDefinition(){
        OperatorType type = OperatorType.BINARY;
        Precedence precedence = Precedence.low();
        Function<OperatorContextImplTest,Double> function = (ctx)->0D;
        int paramsLength = 2;

        OperatorTestImpl definition = new OperatorTestImpl(type,precedence,function,paramsLength);

        assertEquals(type, definition.getType());
        assertEquals(precedence, definition.getPrecedence());
        assertEquals(function, definition.getFunction());
        assertEquals(paramsLength, definition.getParamsLength());
    }

    @Test
    public void testDefinitionHashCode(){
        OperatorType type = OperatorType.BINARY;
        Precedence precedence = Precedence.low();
        Function<OperatorContextImplTest,Double> function = (ctx)->0D;
        int paramsLength = 2;

        OperatorTestImpl definition = new OperatorTestImpl(type,precedence,function,paramsLength);
        assertEquals(Objects.hash(definition.getIdentifier(), type, paramsLength), definition.hashCode());
    }

    @Test
    public void testDefinitionEquals(){
        OperatorType type = OperatorType.BINARY;
        Precedence precedence = Precedence.low();
        Function<OperatorContextImplTest,Double> function = (ctx)->0D;
        int paramsLength = 2;

        OperatorTestImpl definition = new OperatorTestImpl(type,precedence,function,paramsLength);
        OperatorTestImpl definition2 = new OperatorTestImpl(type,null,null,paramsLength);

        assertEquals(definition, definition2);
    }

    @Test
    public void testDefinitionEquals2(){
        OperatorType type = OperatorType.BINARY;
        Precedence precedence = Precedence.low();
        Function<OperatorContextImplTest,Double> function = (ctx)->0D;
        int paramsLength = 2;

        OperatorTestImpl definition = new OperatorTestImpl(type,precedence,function,paramsLength);
        OperatorTestImpl definition2 = new OperatorTestImpl(type,precedence,function,3);

        assertNotEquals(definition, definition2);
    }

    @Test
    public void testDefinitionEquals3(){
        OperatorType type = OperatorType.BINARY;
        Precedence precedence = Precedence.low();
        Function<OperatorContextImplTest,Double> function = (ctx)->0D;
        int paramsLength = 2;

        OperatorTestImpl definition = new OperatorTestImpl(type,precedence,function,paramsLength);
        OperatorTestImpl definition2 = new OperatorTestImpl(OperatorType.UNARY,precedence,function,paramsLength);

        assertNotEquals(definition, definition2);
    }

    static class OperatorContextImplTest extends OperatorContext{
        protected OperatorContextImplTest() {
            super(null);
        }
    }

    static class OperatorTestImpl extends Operator<OperatorContextImplTest> {
        OperatorTestImpl(OperatorType type, Precedence precedence, Function<OperatorContextImplTest, Double> function, int paramsLength) {
            super("", type, precedence, function, paramsLength);
        }
    }
}