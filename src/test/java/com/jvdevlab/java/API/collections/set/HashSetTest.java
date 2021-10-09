package com.jvdevlab.java.API.collections.set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class HashSetTest {

    @Test
    public void canNotGet() {
        Set<String> set = new HashSet<>(Set.of("a", "b", "c"));
        assertTrue(set.contains("a"));

        set.remove("a");
        set.add("a");

        String a = set.stream().filter(e -> e.equals("a")).findFirst().get();
        assertEquals("a", a);
    }

}
