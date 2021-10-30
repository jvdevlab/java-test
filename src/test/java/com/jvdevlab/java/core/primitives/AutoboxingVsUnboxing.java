package com.jvdevlab.java.core.primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AutoboxingVsUnboxing {

    @Test
    public void autoboxing() {
        Integer i = 1;
        assertEquals(Integer.valueOf(1), i);
    }

    @Test
    public void unboxing() {
        int i = Integer.valueOf(1);
        assertEquals(1, i);
    }

    @Test
    public void unboxingNull() {
        Integer iObject = null;
        assertThrows(NullPointerException.class, () -> {
            @SuppressWarnings("all")
            // Warning: Null pointer access: This expression of type Integer is
            // null but
            // requires auto-unboxing
            int i = iObject;
        });
    }

}
