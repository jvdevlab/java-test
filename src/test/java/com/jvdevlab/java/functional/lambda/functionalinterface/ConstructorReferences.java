package com.jvdevlab.java.functional.lambda.functionalinterface;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class ConstructorReferences {

    @Test
    public void constructorReferences() {
        List<String> list = Stream.generate(String::new).limit(3).toList();
        // same as
        list = Stream.generate(() -> new String()).limit(3).toList();

        assertEquals(3, list.size());
    }
}
