package com.xplusj.operator;

import org.junit.Test;

import static org.junit.Assert.*;

public class OperatorPrecedenceTests {

    @Test
    public void testLow(){
        OperatorPrecedence low = OperatorPrecedence.low();

        assertEquals(0, low.compareTo(low));

        assertEquals(-1, low.compareTo(OperatorPrecedence.medium()));
        assertEquals(-1, low.compareTo(OperatorPrecedence.high()));
        assertEquals(-1, low.compareTo(OperatorPrecedence.higher()));
    }

    @Test
    public void testMedium(){
        OperatorPrecedence medium = OperatorPrecedence.medium();

        assertEquals(0, medium.compareTo(medium));

        assertEquals(1, medium.compareTo(OperatorPrecedence.low()));
        assertEquals(-1, medium.compareTo(OperatorPrecedence.high()));
        assertEquals(-1, medium.compareTo(OperatorPrecedence.higher()));
    }

    @Test
    public void testHigh(){
        OperatorPrecedence high = OperatorPrecedence.high();

        assertEquals(0, high.compareTo(high));

        assertEquals(1, high.compareTo(OperatorPrecedence.low()));
        assertEquals(1, high.compareTo(OperatorPrecedence.medium()));
        assertEquals(-1, high.compareTo(OperatorPrecedence.higher()));
    }

    @Test
    public void testHigher(){
        OperatorPrecedence higher = OperatorPrecedence.higher();

        assertEquals(0, higher.compareTo(higher));

        assertEquals(1, higher.compareTo(OperatorPrecedence.low()));
        assertEquals(1, higher.compareTo(OperatorPrecedence.medium()));
        assertEquals(1, higher.compareTo(OperatorPrecedence.high()));
    }

    @Test
    public void testLowerThan(){
        OperatorPrecedence lower = OperatorPrecedence.lowerThan(OperatorPrecedence.low());
        assertEquals(-1, lower.compareTo(OperatorPrecedence.low()));
    }

    @Test
    public void testHigherThan(){
        OperatorPrecedence higher = OperatorPrecedence.higherThan(OperatorPrecedence.low());
        assertEquals(1, higher.compareTo(OperatorPrecedence.low()));
    }

    @Test
    public void testSameAs(){
        OperatorPrecedence same = OperatorPrecedence.sameAs(OperatorPrecedence.low());
        assertEquals(0, same.compareTo(OperatorPrecedence.low()));
    }
}