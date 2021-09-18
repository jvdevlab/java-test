package com.jvdevlab.java.stream.operations.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class FlatMapFunction {

    @Test
    public void flatMap() {
        List<List<String>> strings = List.of(List.of("1", "2", "3"), List.of("4", "5", "6"));

        List<String> flatString = strings.stream().flatMap(s -> s.stream()).collect(Collectors.toList());
        assertEquals(6, flatString.size());
        assertEquals("6", flatString.get(flatString.size() - 1));

        // If you need to do type conversion you would need to call map on each of these sub-streams.  
        List<Integer> integers = strings.stream().flatMap(s -> s.stream().map(Integer::parseInt))
                .collect(Collectors.toList());
        assertEquals(6, integers.size());
        assertEquals(6, integers.get(integers.size() - 1));
    }

}
