package com.jvdevlab.java.API.collections.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

public class ArrayListTest {

    @Test
    public void arrayList() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        new CopyOnWriteArrayList<>(list);

        // size
        assertEquals(3, list.size());

        // access
        assertEquals("c", list.get(2));

        Iterator<String> it = list.iterator();
        assertTrue(it.hasNext());

        // iterate backwards
        ListIterator<String> listIt = list.listIterator();
        listIt.next();
        assertTrue(listIt.hasPrevious());

        // removal
        assertTrue(list.remove("b"));
        assertFalse(list.contains("b"));

        assertEquals("c", list.remove(1));
        assertFalse(list.contains("c"));

        // modification
        assertEquals("a", list.set(0, "e"));

        // clearing
        list.clear();
        assertTrue(list.isEmpty());

        // ArrayList is pretty much Vector but not-synchronized.
    }

    @Test
    public void asList() {
        List<String> list = Arrays.asList("a", "b", "c");

        // It's not an ArrayList it is java.util.Arrays$ArrayList
        assertFalse(list instanceof ArrayList);

        assertThrows(UnsupportedOperationException.class, () -> {
            list.add("d");
        });
    }


}
