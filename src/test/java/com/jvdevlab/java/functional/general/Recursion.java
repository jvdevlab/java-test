package com.jvdevlab.java.functional.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class Recursion {

    // have to wrap lambda into this to give it a name
    // as lambda doesn't have it's own this.
    class Recursive<I> {
        I func;
    }

    @Test
    public void headRecursion() {
        Recursive<Function<Integer, Integer>> recursive = new Recursive<>();
        recursive.func = (n) -> (n == 1) ? 1 : n * recursive.func.apply(n - 1);

        assertEquals(24, recursive.func.apply(4));
    }

    @Test
    public void tailRecursion() {
        Recursive<BiFunction<Integer, Integer, Integer>> recursive = new Recursive<>();
        recursive.func = (n, accumulator) -> (n == 1) ? accumulator
                : recursive.func.apply(n - 1, n * accumulator);

        assertEquals(24, recursive.func.apply(4, 1));
    }
}
