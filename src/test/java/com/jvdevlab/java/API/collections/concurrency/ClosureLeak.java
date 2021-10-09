package com.jvdevlab.java.API.collections.concurrency;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class ClosureLeak {

    @Test
    public void closureLeak() throws InterruptedException {

        Supplier<List<Boolean>> fun = method();
        // after method() is executed the methodLocalVariable suppose to go out
        // of scope, but it leaked via closure.

        Thread thread = new Thread(() -> {
            List<Boolean> leakedVariable = fun.get();
            assertTrue(leakedVariable.get(0));
            leakedVariable.set(0, false);
            assertFalse(leakedVariable.get(0));
        });
        thread.start();

        Thread.sleep(500);
    }

    private Supplier<List<Boolean>> method() {
        List<Boolean> methodLocalVariable = new ArrayList<>();
        methodLocalVariable.add(true);

        // Closure closes over methodLocalVariable aka captures it.
        Supplier<List<Boolean>> fun = () -> methodLocalVariable;

        return fun;
    }

}
