package com.jvdevlab.java.API.collections.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

public class FailFastVsStronglyConsistentVsWeaklyConsistentIterator {

    @Test
    public void failFast() {
        List<String> strings = new ArrayList<>();
        strings.add("a");

        assertThrows(ConcurrentModificationException.class, () -> {
            Iterator<String> it = strings.iterator();
            while (it.hasNext()) {
                strings.add("b");
                it.next();
            }
        });
    }

    @Test
    public void stronglyConsistent() {
        List<String> strings = new CopyOnWriteArrayList<>();
        strings.add("a");

        StringBuilder sb = new StringBuilder();
        Iterator<String> it = strings.iterator();
        while (it.hasNext()) {
            strings.add("b");
            assertEquals(2, strings.size());
            sb.append(it.next());
        }

        // "b" wasn't visible via iterator.
        assertEquals("a", sb.toString());
    }

    @Test
    public void weaklyConsistent() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "a");

        StringBuilder sb = new StringBuilder();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            map.put("b", "b");
            assertEquals(2, map.size());
            sb.append(it.next());
        }

        // "b" might be visible via iterator.
        assertEquals("ab", sb.toString());
    }

}
