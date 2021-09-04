package com.jvdevlab.java.core.primitives;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class AutoboxingVsUnboxing {

    int echoInteger(Integer i) {
        return i;
    }

    Integer echoInt(int i) {
        return i;
    }

    @Test
    public void autoboxing() {
        int i = 1;
        Integer iObject = i;
        iObject = 1;
        iObject = echoInteger(1); // boxed > unboxed > boxed.

        List<Integer> list = new ArrayList<>();
        list.add(1);

        assertEquals(1, list.get(0));
        assertEquals(1, iObject);
    }

    @Test
    public void unboxing() {
        Integer iObject = Integer.valueOf(1);
        int i = iObject;
        i = Integer.valueOf(1);
        i = echoInt(iObject); // unboxed > boxed > unboxed.

        List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(1));
        i = list.get(0);

        assertTrue(1 == i);
        assertTrue(i == iObject.intValue());
    }

    @Test
    public void unboxingNull() {
        Integer iObject = null;
        assertThrows(NullPointerException.class, () -> {
            @SuppressWarnings("all")
            // Warning: Null pointer access: This expression of type Integer is null but
            // requires auto-unboxing
            int i = iObject;
        });
    }

    @Test
    public void unboxingAndEquality() {
        // <, >, <= and >= work only with primitives.
        // So Java will unbox.
        assertTrue(Integer.valueOf(1) < Integer.valueOf(2));
        assertTrue(Integer.valueOf(1) <= Integer.valueOf(2));
        assertTrue(Integer.valueOf(2) > Integer.valueOf(1));
        assertTrue(Integer.valueOf(2) >= Integer.valueOf(1));

        // But == and != defined for references too.
        // No unboxing.
        assertFalse(Integer.valueOf(1000) == Integer.valueOf(1000));
        assertTrue(Integer.valueOf(1000) != Integer.valueOf(1000));

        // But again there is a caveat for @see WrapperClasses.wrappersAreCaching
        assertTrue(Integer.valueOf(1) == Integer.valueOf(1));
        assertFalse(Integer.valueOf(1) != Integer.valueOf(1));

        // So stick to equals!
    }

    public void atomicWrappersDontDoBoxing() {
        @SuppressWarnings("unused")
        AtomicInteger iObject = new AtomicInteger(1);
        // Type mismatch: cannot convert from int to AtomicInteger
        // iObject = 1;
        // Type mismatch: cannot convert from AtomicInteger to int
        // int i = iObject;
    }

}
