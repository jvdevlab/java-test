package com.jvdevlab.java.API.collections.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HashMapTest {

   
    @Test
    public void allowsNullKeyAndValue() {
        Map<String, String> map = new HashMap<>();
        map.put(null, null);

        assertEquals(null, map.get(null));
    }
}
