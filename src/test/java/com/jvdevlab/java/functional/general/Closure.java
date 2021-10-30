package com.jvdevlab.java.functional.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class Closure {

    @Test
    public void closure() {
        assertEquals(4, function().apply(2));
    }

    public Function<Integer, Integer> function() {
        final int thisVarWillGoOutOfScope = 2;
        Function<Integer, Integer> nestedFunction = i -> i * thisVarWillGoOutOfScope;
        return nestedFunction;
    }
}
