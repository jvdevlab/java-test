package com.jvdevlab.java.functional.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

public class FunctionComposition {

    @Test
    public void compose() {
        Function<Integer, Integer> timesTwo = (e) -> e * 2;
        Function<Integer, Integer> addTwo = (e) -> e + 2;

        Function<Integer, Integer> addTwoThenTimesTwo = timesTwo
                .compose(addTwo);
        Function<Integer, Integer> timeTwoThenAddTwo = timesTwo.andThen(addTwo);

        assertEquals(8, addTwoThenTimesTwo.apply(2));
        assertEquals(6, timeTwoThenAddTwo.apply(2));
    }

    @Test
    public void predicate() {
        Predicate<String> isNull = (e) -> e == null;
        Predicate<String> isEmpty = (e) -> e == "";
        Predicate<String> isNotBlank = isNull.negate().and(isEmpty.negate());

        assertFalse(isNotBlank.test(null));
        assertFalse(isNotBlank.test(""));
        assertTrue(isNotBlank.test("a"));
    }
}
