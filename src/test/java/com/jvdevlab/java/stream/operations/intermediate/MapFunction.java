package com.jvdevlab.java.stream.operations.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class MapFunction {

    @Test
    public void map() {
        List<String> strings = List.of("1", "2", "3");
        List<Integer> integers = strings.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        assertEquals(3, integers.size());
        assertEquals(3, integers.get(2));

        // Same with method reference
        integers = strings.stream().map(Integer::parseInt).collect(Collectors.toList());
        assertEquals(1, integers.get(0));
    }

}
