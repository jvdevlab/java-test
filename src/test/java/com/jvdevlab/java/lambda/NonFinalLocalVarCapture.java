package com.jvdevlab.java.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class NonFinalLocalVarCapture {

    @Test
    public void dontCaptureNonFinalLocalVarsInClosure() {

        final int expectedTotal = 10;
        @SuppressWarnings("unused")
        int sum = 0;
        List<Integer> numbers = Arrays.asList(3, 2, 5);
        // Can't do this. Illegal in Java. Compiler error:
        // "Local variable sum defined in an enclosing scope must be final or
        // effectively final."
        // numbers.forEach(n -> sum += n);

        // This workaround will work BUT you must NOT do this!
        // Non final local var shouldn't leak with closure this will make things not
        // thread safe.
        final int[] sum2 = { 0 };
        numbers.forEach(n -> sum2[0] += n);
        assertEquals(expectedTotal, sum2[0]);

        // This is better but still discouraged as it reduces parallelism.
        final AtomicInteger sum3 = new AtomicInteger();
        numbers.forEach(n -> sum3.addAndGet(n));
        assertEquals(expectedTotal, sum3.get());

        // Instead use FP concepts available to you. E.g. IntStream has a sum() method.
        // It works with int array so need to do conversion first.
        int sum4 = numbers.stream().mapToInt(i -> i).sum();
        assertEquals(expectedTotal, sum4);

        // And if there is no out-of-the-box method you can come up with a solution by
        // using corner stone FP functions such as: reduce, map, etc.
        int sum5 = numbers.stream().reduce(0, (total, next) -> total + next);
        assertEquals(expectedTotal, sum5);

        // Or using method reference as accumulator function.
        int sum6 = numbers.stream().reduce(0, Integer::sum);
        assertEquals(expectedTotal, sum6);

        // From the JavaDocs:
        // "Sum, min, max, average, and string concatenation are all special
        // cases of reduction."
        // "While this may seem a more roundabout way to perform an aggregation
        // compared to simply mutating a running total in a loop, reduction
        // operations parallelize more gracefully, without needing additional
        // synchronization and with greatly reduced risk of data races."
    }
}
