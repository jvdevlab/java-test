package com.jvdevlab.java.functional.stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class ParallelStream {

    @Test
    public void sequential() {
        StringBuilder threadName = new StringBuilder();

        IntStream.range(0, 30).forEach(
                (e) -> threadName.append(Thread.currentThread().getName()));

        assertTrue(threadName.toString().startsWith("main"));
    }

    @Test
    public void parallel() {
        StringBuilder threadName = new StringBuilder();

        IntStream.range(0, 30).parallel().forEach(
                (e) -> threadName.append(Thread.currentThread().getName()));

        assertTrue(threadName.toString().contains("ForkJoinPool"));
    }
}
