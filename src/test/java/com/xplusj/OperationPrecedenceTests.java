package com.xplusj;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OperationPrecedenceTests {

    @Test
    public void testLow(){
        OperationPrecedence low = OperationPrecedence.low();

        assertEquals(0, low.compareTo(low));

        assertEquals(-1, low.compareTo(OperationPrecedence.medium()));
        assertEquals(-1, low.compareTo(OperationPrecedence.high()));
        assertEquals(-1, low.compareTo(OperationPrecedence.higher()));
    }

    @Test
    public void testMedium(){
        OperationPrecedence medium = OperationPrecedence.medium();

        assertEquals(0, medium.compareTo(medium));

        assertEquals(1, medium.compareTo(OperationPrecedence.low()));
        assertEquals(-1, medium.compareTo(OperationPrecedence.high()));
        assertEquals(-1, medium.compareTo(OperationPrecedence.higher()));
    }

    @Test
    public void testHigh(){
        OperationPrecedence high = OperationPrecedence.high();

        assertEquals(0, high.compareTo(high));

        assertEquals(1, high.compareTo(OperationPrecedence.low()));
        assertEquals(1, high.compareTo(OperationPrecedence.medium()));
        assertEquals(-1, high.compareTo(OperationPrecedence.higher()));
    }

    @Test
    public void testHigher(){
        OperationPrecedence higher = OperationPrecedence.higher();

        assertEquals(0, higher.compareTo(higher));

        assertEquals(1, higher.compareTo(OperationPrecedence.low()));
        assertEquals(1, higher.compareTo(OperationPrecedence.medium()));
        assertEquals(1, higher.compareTo(OperationPrecedence.high()));
    }

    @Test
    public void testLowerThan(){
        OperationPrecedence lower = OperationPrecedence.lowerThan(OperationPrecedence.low());
        assertEquals(-1, lower.compareTo(OperationPrecedence.low()));
    }

    @Test
    public void testHigherThan(){
        OperationPrecedence higher = OperationPrecedence.higherThan(OperationPrecedence.low());
        assertEquals(1, higher.compareTo(OperationPrecedence.low()));
    }

    @Test
    public void testSameAs(){
        OperationPrecedence same = OperationPrecedence.sameAs(OperationPrecedence.low());
        assertEquals(0, same.compareTo(OperationPrecedence.low()));
    }
}