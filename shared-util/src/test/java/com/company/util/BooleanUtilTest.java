package com.company.util;

import org.junit.Assert;
import org.junit.Test;

import com.company.util.BooleanUtil;

public class BooleanUtilTest {

    @Test
    public void testGetBoolean() {
        Assert.assertFalse(BooleanUtil.getBoolean(null));
        Assert.assertFalse(BooleanUtil.getBoolean((Boolean) null));
        Assert.assertFalse(BooleanUtil.getBoolean(false));
        Assert.assertFalse(BooleanUtil.getBoolean("false"));
        Assert.assertFalse(BooleanUtil.getBoolean(Boolean.FALSE));
        
        
        Assert.assertTrue(BooleanUtil.getBoolean(true));
        Assert.assertTrue(BooleanUtil.getBoolean("true"));
        Assert.assertTrue(BooleanUtil.getBoolean(Boolean.TRUE));
    }

}
