package com.xplusj.operator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PrecedenceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLow(){
        Precedence low = Precedence.low();

        assertEquals(0, low.compareTo(low));

        assertEquals(-1, low.compareTo(Precedence.medium()));
        assertEquals(-1, low.compareTo(Precedence.high()));
        assertEquals(-1, low.compareTo(Precedence.highest()));
    }

    @Test
    public void testMedium(){
        Precedence medium = Precedence.medium();

        assertEquals(0, medium.compareTo(medium));

        assertEquals(1, medium.compareTo(Precedence.low()));
        assertEquals(-1, medium.compareTo(Precedence.high()));
        assertEquals(-1, medium.compareTo(Precedence.highest()));
    }

    @Test
    public void testHigh(){
        Precedence high = Precedence.high();

        assertEquals(0, high.compareTo(high));

        assertEquals(1, high.compareTo(Precedence.low()));
        assertEquals(1, high.compareTo(Precedence.medium()));
        assertEquals(-1, high.compareTo(Precedence.highest()));
    }

    @Test
    public void testHigher(){
        Precedence higher = Precedence.highest();

        assertEquals(0, higher.compareTo(higher));

        assertEquals(1, higher.compareTo(Precedence.low()));
        assertEquals(1, higher.compareTo(Precedence.medium()));
        assertEquals(1, higher.compareTo(Precedence.high()));
    }

    @Test
    public void testLowerThan(){
        Precedence lower = Precedence.lowerThan(Precedence.low());
        assertEquals(-1, lower.compareTo(Precedence.low()));
    }

    @Test
    public void testHigherThan(){
        Precedence higher = Precedence.higherThan(Precedence.low());
        assertEquals(1, higher.compareTo(Precedence.low()));
    }

    @Test
    public void testSameAs(){
        Precedence same = Precedence.sameAs(Precedence.low());
        assertEquals(0, same.compareTo(Precedence.low()));
    }

    @Test
    public void testMinPrecedenceException(){
        thrown.expectMessage("Precedence already with LOWEST value");
        Precedence.lowerThan(Precedence.lowest());
    }

    @Test
    public void testMaxPrecedenceException(){
        thrown.expectMessage("Precedence already with HIGHEST value");
        Precedence.higherThan(Precedence.highest());
    }
}