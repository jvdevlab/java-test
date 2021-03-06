package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

public class TryWithResources {

    @Test
    public void oldWayOfClosingAResource() {
        FileReader reader = null;
        try {
            reader = new FileReader("@");
            reader.read();
        } catch (Exception e) {
            // do nothing
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                // do nothing
            }
        }
        assertNull(reader);
    }

    @Test
    public void newWayOfClosingAResource() {
        try (FileReader reader = new FileReader("@")) {
            reader.read();
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void newWayOfClosingAResourceImplicitly() {
        // Anything that implement AutoCloseable/Closeable can be used in
        // try-with-resources contract. And because these are Functional Interfaces
        // they can be used in Lambda expression.

        final AtomicBoolean closed = new AtomicBoolean(false);

        try (Closeable resource = () -> closed.set(true);) {
            // do nothing.
        } catch (Exception e) {
            // do nothing.
        }

        assertTrue(closed.get());
    }

    @Test
    public void multipleResources() {
        try (FileReader reader = new FileReader("@"); FileWriter writer = new FileWriter("@")) {
            writer.write(reader.read());
        } catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException);
        }
    }

    @Test
    public void multipleResourcesCloseInReverseOrder() {
        final List<String> closureOrder = new ArrayList<>();

        try (Closeable reader = () -> closureOrder.add("reader"); Closeable writer = () -> closureOrder.add("writer")) {
            // do nothing
        } catch (Exception e) {
            // do nothing
        }

        assertEquals("writer", closureOrder.get(0));
        assertEquals("reader", closureOrder.get(1));
    }

    @Test
    public void ifTheConstructorThrowsAnException() {
        AtomicBoolean isClosed = new AtomicBoolean(false);

        class ResourceA implements Closeable {
            public ResourceA() {
                throw new RuntimeException("Constructor");
            }

            @Override
            public void close() {
                isClosed.set(true);
            }
        }

        // Exception propagates
        assertThrows(RuntimeException.class, () -> {
            try (ResourceA resource = new ResourceA()) {
                // do nothing
            }
        });

        // Resource is not closed.
        assertFalse(isClosed.get());
    }

    @Test
    public void ifTheCallOnAResourceThrowsAnException() {
        AtomicBoolean isClosed = new AtomicBoolean(false);

        // Exception propagates
        assertThrows(Exception.class, () -> {
            try (Closeable resource = () -> isClosed.set(true);) {
                throw new Exception();
            }
        });

        // Resource is closed.
        assertTrue(isClosed.get());
    }

    @Test
    public void ifTheImplicitCallToCloseThrowsAnException() {
        // Exception propagates
        assertThrows(RuntimeException.class, () -> {
            try (Closeable resource = () -> {
                throw new RuntimeException();
            }) {
                // do nothing
            }
        });
    }

    @Test
    public void java9Enhancements() {
        // "Before Java 9 a resource that is to be automatically closed must be created
        // inside the parentheses of the try block of a try-with-resources construct.
        // From Java 9, this is no longer necessary. If the variable referencing the
        // resource is final or effectively final, you can simply enter a reference to
        // the variable inside the try block parentheses."

        // this is "final" for another reason (closure requirements)
        AtomicBoolean isClosed = new AtomicBoolean(false);

        // Didn't explicitly marked as final.
        Closeable resource = () -> isClosed.set(true);

        // This will give compiler error as the resource var will stop
        // being effectively-final after re-assignment.
        // resource = null;

        try (resource) {
            // do nothing.
        } catch (Exception e) {
            // do nothing.
        }

        // resource available outside of the scope of the Try block
        assertNotNull(resource);
        // resource is still closed
        assertTrue(isClosed.get());
    }

}
