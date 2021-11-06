package com.jvdevlab.java.functional.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class Monads {

    // https://medium.com/@afcastano/monads-for-java-developers-part-1-the-optional-monad-aa6e797b8a6e
    @Test
    public void monadsInJava() {
        // non-monad way we have to "unwrap the Integer from the Optional
        // context"
        assertEquals(5,
                noMonadOptionalAdd(Optional.of(2), Optional.of(3)).get());

        // monad way helps us "avoid dealing with the context when composing
        // parametrized types" i.e. we didn't use isPresent and get to unwrap 
        // as flatMap + composition did it for us.
        assertEquals(5, monadOptionalAdd(Optional.of(2), Optional.of(3)).get());
    }

    public Optional<Integer> noMonadOptionalAdd(Optional<Integer> val1,
            Optional<Integer> val2) {
        if (val1.isPresent() && val2.isPresent()) {
            return Optional.of(val1.get() + val2.get());
        }

        return Optional.empty();
    }

    public Optional<Integer> monadOptionalAdd(Optional<Integer> val1,
            Optional<Integer> val2) {
        return val1.flatMap(
                first -> val2.flatMap(second -> Optional.of(first + second)));
    }

}
