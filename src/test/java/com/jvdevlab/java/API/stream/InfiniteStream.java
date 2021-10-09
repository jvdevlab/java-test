package com.jvdevlab.java.API.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class InfiniteStream {

    @Test
    public void iterate() {
        Stream<Integer> infinite = Stream.iterate(0, i -> i + 1);
        List<Integer> result = infinite.limit(3).toList();
        assertEquals(List.of(0, 1, 2), result);
    }

    @Test
    public void generate() {
        Random random = new Random();
        Stream<Integer> infinite = Stream.generate(random::nextInt);
        List<Integer> result = infinite.limit(3).toList();
        assertEquals(3, result.size());
    }
}
