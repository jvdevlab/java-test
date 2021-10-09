package com.jvdevlab.java.API.collections.map;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;

public class ConcurrentHashMapTest {

    @Test
    public void doesNotAllowNullKeyAndValue() {
        Map<String, String> map = new ConcurrentHashMap<>();

        assertThrows(NullPointerException.class, () -> {
            map.put(null, "test");
        });
        assertThrows(NullPointerException.class, () -> {
            map.put("test", null);
        });
    }

}
