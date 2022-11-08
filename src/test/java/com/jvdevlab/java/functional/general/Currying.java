package com.jvdevlab.java.functional.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class Currying {

    @Test
    public void currying() {
        interface Function3Arity<A, B, C, R> {
            R apply(A a, B b, C c);
        }

        Function3Arity<String, String, String, String> func = (a, b, c) -> a + b
                + c;

        Function<String, Function<String, Function<String, String>>> func2 = a -> b -> c -> a
                + b + c;

        assertEquals(func.apply("a", "b", "c"),
                func2.apply("a").apply("b").apply("c"));
        // in JS would look more simpler
        // func("a", "b", "c") vs. func2("a")("b")("c")
    }

}
