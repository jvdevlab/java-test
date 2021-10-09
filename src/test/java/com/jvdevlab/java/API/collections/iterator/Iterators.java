package com.jvdevlab.java.API.collections.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

public class Iterators {

    @Test
    public void listIterator() {
        List<Integer> list = List.of(1, 2, 3);
        ListIterator<Integer> iterator = list.listIterator();

        assertEquals(1, iterator.next());
        assertEquals(1, iterator.previous());
    }
}
