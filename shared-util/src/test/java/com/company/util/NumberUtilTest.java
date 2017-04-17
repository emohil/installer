package com.company.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.company.util.NumberUtil;

public class NumberUtilTest {

    @Test
    public void testTo36HexStringUpper() {
        
        assertEquals("Z", NumberUtil.to36HexStringUpper(35));
        assertEquals("1CZ", NumberUtil.to36HexStringUpper(1763));
    }

    @Test
    public void testTo36HexString() {
        assertEquals("z", NumberUtil.to36HexString(35));
        assertEquals("1cz", NumberUtil.to36HexString(1763));
    }

    @Test
    public void testToIntRadix36String() {
        assertEquals(1763, NumberUtil.toIntRadix36("00001CZ"));
        assertEquals(1763, NumberUtil.toIntRadix36("001cz"));
        assertEquals(1763, NumberUtil.toIntRadix36("1CZ"));
    }
}