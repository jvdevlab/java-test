package com.jvdevlab.java.API.collections.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

public class TreeMapTest {

    @Test
    public void doesNotAllowNullKey() {
        Map<String, String> map = new TreeMap<>();
        assertThrows(NullPointerException.class, () -> map.put(null, ""));
    }

    @Test
    public void allowsNullValue() {
        Map<String, String> map = new TreeMap<>();
        map.put("", null);
        assertEquals(null, map.get(""));
    }

}
