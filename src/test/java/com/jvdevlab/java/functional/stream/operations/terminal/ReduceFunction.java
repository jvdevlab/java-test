package com.jvdevlab.java.functional.stream.operations.terminal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class ReduceFunction {

    @Test
    public void reduce() {
        int sum = IntStream.rangeClosed(1, 3).reduce(0, (i, j) -> i + j);
        assertEquals(6, sum);
    }
}
