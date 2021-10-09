package com.jvdevlab.java.API.collections.list;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class ImmutableList {

    @Test
    public void unmodifiableList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        List<Integer> unmodifiableList = Collections.unmodifiableList(list);
        assertThrows(UnsupportedOperationException.class,
                () -> unmodifiableList.add(2));

        // Static factory
        List<Integer> l = List.of(1, 2, 3);
        assertThrows(UnsupportedOperationException.class, () -> l.add(2));

        // Streams
        List<Integer> l2 = IntStream.range(0, 5).mapToObj(Integer::valueOf)
                .collect(Collectors.toUnmodifiableList());
        assertThrows(UnsupportedOperationException.class, () -> l2.add(2));
    }
}
