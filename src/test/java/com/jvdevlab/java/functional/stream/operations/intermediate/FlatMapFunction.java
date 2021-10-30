package com.jvdevlab.java.functional.stream.operations.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class FlatMapFunction {

        @Test
        public void flatMap() {
                List<List<String>> strings = List.of( //
                                List.of("1", "2", "3"), //
                                List.of("4", "5", "6"));

                List<String> flatString = strings.stream()
                                .flatMap(s -> s.stream()).toList();
                assertEquals(6, flatString.size());
                assertEquals("1", flatString.get(0));

                // If you need to do type conversion you would need to call
                // map on each of these sub-streams.
                List<Integer> integers = strings.stream()
                                .flatMap(s -> s.stream().map(Integer::parseInt))
                                .toList();
                assertEquals(6, integers.size());
                assertEquals(1, integers.get(0));
        }

}
