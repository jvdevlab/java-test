package com.jvdevlab.java.jvm;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IntegerPool {

    @Test
    public void integerPool() {
        assertFalse(Integer.valueOf(777) == Integer.valueOf(777));

        // -128 to 127 are cached.
        assertTrue(Integer.valueOf(127) == Integer.valueOf(127));
    }
}
