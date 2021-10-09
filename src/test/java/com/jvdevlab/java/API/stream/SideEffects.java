package com.jvdevlab.java.API.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class SideEffects {

    @Test
    public void noSideEffectsForTheOriginalCollection() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        long count = list.stream().filter(e -> e < 2).count();
        assertEquals(1, count);
        // filter didn't modify original list. No side effects
        assertTrue(list.contains(2));
    }

    @Test
    public void sideEffectsForWhenMutatingObjects() {
        // Any class that we can mutate
        List<AtomicInteger> list = new ArrayList<>();
        list.add(new AtomicInteger(1));
        list.add(new AtomicInteger(2));

        // Introduce side effect
        long count = list.stream().filter(e -> e.incrementAndGet() == 0)
                .count();
        assertEquals(0, count);
        assertEquals(2, list.get(0).get()); // mutated
    }
}
