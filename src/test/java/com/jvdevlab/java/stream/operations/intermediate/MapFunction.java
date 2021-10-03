package com.jvdevlab.java.stream.operations.intermediate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class MapFunction {

    @Test
    public void map() {
        List<String> strings = List.of("1", "2", "3");
        List<Integer> integers = strings.stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());
        assertEquals(1, integers.get(0));

        // Same with method reference and new toList method
        integers = strings.stream().map(Integer::parseInt).toList();
        assertEquals(1, integers.get(0));
    }

    @Test
    public void mappingOverDoesNotHaveToTransformToAnotherType() {
        List<String> list = List.of("1", "2", "3");
        List<String> clone = list.stream().map((s) -> s)
                .collect(Collectors.toList());
        assertTrue(list != clone);
        assertEquals(list, clone);
    }

}
