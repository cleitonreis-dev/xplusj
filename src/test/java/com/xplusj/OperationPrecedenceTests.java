package com.xplusj;

import com.xplusj.operation.OperationPrecedence;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class OperationPrecedenceTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLow(){
        OperationPrecedence low = OperationPrecedence.low();

        assertEquals(0, low.compareTo(low));

        assertEquals(-1, low.compareTo(OperationPrecedence.medium()));
        assertEquals(-1, low.compareTo(OperationPrecedence.high()));
        assertEquals(-1, low.compareTo(OperationPrecedence.highest()));
    }

    @Test
    public void testMedium(){
        OperationPrecedence medium = OperationPrecedence.medium();

        assertEquals(0, medium.compareTo(medium));

        assertEquals(1, medium.compareTo(OperationPrecedence.low()));
        assertEquals(-1, medium.compareTo(OperationPrecedence.high()));
        assertEquals(-1, medium.compareTo(OperationPrecedence.highest()));
    }

    @Test
    public void testHigh(){
        OperationPrecedence high = OperationPrecedence.high();

        assertEquals(0, high.compareTo(high));

        assertEquals(1, high.compareTo(OperationPrecedence.low()));
        assertEquals(1, high.compareTo(OperationPrecedence.medium()));
        assertEquals(-1, high.compareTo(OperationPrecedence.highest()));
    }

    @Test
    public void testHigher(){
        OperationPrecedence higher = OperationPrecedence.highest();

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

    @Test
    public void testMinPrecedenceException(){
        thrown.expectMessage("Precedence already with LOWEST value");
        OperationPrecedence.lowerThan(OperationPrecedence.lowest());
    }

    @Test
    public void testMaxPrecedenceException(){
        thrown.expectMessage("Precedence already with HIGHEST value");
        OperationPrecedence.higherThan(OperationPrecedence.highest());
    }
}