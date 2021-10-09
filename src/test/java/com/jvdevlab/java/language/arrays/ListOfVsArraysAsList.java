package com.jvdevlab.java.language.arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ListOfVsArraysAsList {

    @Test
    public void arrayAsList() {
        Integer[] a = { 1, 2, 3 };
        List<Integer> l = Arrays.asList(a);
        // Can't do structural modifications
        assertThrows(UnsupportedOperationException.class, () -> l.add(4));

        // Changes are propagated back-and-forth.
        a[0] = 0;
        assertEquals(0, l.get(0));
        l.set(0, 1);
        assertEquals(1, a[0]);

        // Allows nulls
        List<Integer> l2 = Arrays.asList(null, null);
        assertEquals(null, l2.get(0));
    }

    @Test
    @SuppressWarnings("unused")
    public void listOf() {
        List<Integer> l = List.of(1, 2, 3);
        // Immutable
        assertThrows(UnsupportedOperationException.class, () -> l.set(0, 0));

        // Doesn't propagate changes to a passed array as it is immutable!
        Integer[] a = new Integer[] { 1, 2, 3 };
        List<Integer> l2 = List.of(a);
        // l2.clear(); can't do that

        // Doesn't allow null
        assertThrows(NullPointerException.class, () -> List.of(null, null));
    }

}
