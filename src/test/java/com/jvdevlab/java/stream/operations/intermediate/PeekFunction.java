package com.jvdevlab.java.stream.operations.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class PeekFunction {

    @Test
    public void peek() {
        Integer sum = IntStream.range(1, 10).peek(x -> {
            // Set a break point here
            System.out.println(x);
        }).sum();
        assertEquals(45, sum);
    }

    @Test
    public void parallelPeek() {
        Integer sum = IntStream.range(1, 10).parallel().peek(x -> System.out.println(Thread.currentThread().getName()))
                .sum();
        assertEquals(45, sum);
    }

}
