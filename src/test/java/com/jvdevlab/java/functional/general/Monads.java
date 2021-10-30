package com.jvdevlab.java.functional.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class Monads {

    // https://www.baeldung.com/java-functional-programming
    @Test
    public void monadsInJava() {
        Optional<Integer> result = Optional.of(2).flatMap(f -> Optional.of(3).flatMap(s -> Optional.of(f + s)));
        assertEquals(5, result.get());
    }

}
