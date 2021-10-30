package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ThrowVsThrowsVsThrowable {

    @Test
    public void throwIsAStatementThatCausesAnExceptionToBeThrown() {
        assertThrows(Exception.class, () -> {
            throw new Exception();
        });
    }

    @Test
    public void throwsIndicateAnExceptionsMayBeThrownByAMethod() {
        class ClassA {
            public void method() throws IOException {
                throw new IOException();
            }
        }

        ClassA a = new ClassA();
        assertThrows(IOException.class, () -> a.method());

        // Compiler error: Unhandled exception type IOException
        // a.method();
    }

    @Test
    public void throwableIsTheRootClassOfAllObjectsThatCanBeThrown() {
        Exception e = new Exception();
        assertTrue(e instanceof Throwable);

        // Note, catching Throwable is considered bad practice.
    }

    @Test
    public void canNotThrowNonThrowable() {
        // Compiler error: No exception of type String can be thrown; an exception type
        // must be a subclass of Throwable
        // throw new String();
    }

}
