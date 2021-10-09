package com.jvdevlab.java.language.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class InstanceOf {

    @Test
    public void instanceOf() {
        Number n = Integer.valueOf(42);

        // before
        if (n instanceof Integer) {
            Integer i = (Integer) n;
            assertEquals(42, i);
        }

        // after
        if (n instanceof Integer i) {
            assertEquals(42, i);
        }
    }
}
