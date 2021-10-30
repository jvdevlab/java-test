package com.jvdevlab.java.functional.lambda.functionalinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class MethodReferences {

    @Test
    public void classStaticMethod() {
        List<String> strings = List.of("1", "2", "3");

        List<Integer> numbers = strings.stream().map(Integer::parseInt)
                .toList();
        // same as
        numbers = strings.stream().map(input -> Integer.parseInt(input))
                .toList();

        assertEquals(1, numbers.get(0));
    }

    @Test
    public void classInstanceMethod() {
        List<String> strings = List.of("A", "B", "C");

        List<String> lowercase = strings.stream().map(String::toLowerCase)
                .toList();
        // same as
        lowercase = strings.stream().map(e -> e.toLowerCase()).toList();

        assertEquals("a", lowercase.get(0));
    }

    @Test
    public void objectInstanceMethod() {
        Random random = new Random();

        List<Integer> list = IntStream.generate(random::nextInt).limit(3)
                .boxed().toList();
        // same as
        list = IntStream.generate(() -> random.nextInt()).limit(3).boxed()
                .toList();

        assertEquals(3, list.size());
    }

}
