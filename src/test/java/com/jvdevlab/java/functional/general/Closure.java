package com.jvdevlab.java.functional.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class Closure {

    @Test
    public void closure() {
        // How does it return 4 if thisVarWillGoOutOfScope is out of scope?
        // Answer: closure aka a backpack is returned with the function
        // that contains the snapshot of thisVarWillGoOutOfScope.
        assertEquals(4, outer().apply(2));
    }

    public Function<Integer, Integer> outer() {
        // Create a new variable on the heap.
        // This variable will go out of scope when the outer is executed.
        final Integer thisVarWillGoOutOfScope = Integer.valueOf(2);
        Function<Integer, Integer> inner = i -> i * thisVarWillGoOutOfScope;
        return inner;
    }
}
