package com.jvdevlab.java.functional.stream.operations.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class FilterFunction {

    @Test
    public void filter() {
        // Note, have to use boxed() to convert IntStream to Stream.
        // As IntSteam.collect() doesn't accept Collector.
        Set<Integer> even = IntStream.rangeClosed(1, 10).filter(i -> i % 2 == 0)
                .boxed().collect(Collectors.toSet());
        assertEquals(5, even.size());
        // Set doesn't have get() method!
        assertEquals(2, even.stream().filter(Integer.valueOf(2)::equals)
                .findFirst().get());
    }
}
