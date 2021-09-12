package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuppressedExceptions {

    @Test
    public void suppressedExceptions() {
        String stackTrace = "";

        try (AutoCloseable r = () -> {
            throw new Exception("Close Exception");
        }) {
            throw new Exception("Resource Exception");
        } catch (Exception e) {
            // The first exception "wins"
            // Resource Exception was first so it wins. As "Java only allows for one
            // exception to propagate, the other exceptions are suppressed."
            assertEquals("Resource Exception", e.getMessage());
            // Close Exception was suppressed.
            assertEquals("Close Exception", e.getSuppressed()[0].getMessage());

            stackTrace = ExceptionUtils.readStackTrace(e);
        }

        // Suppressed exception is added to the stack trace.
        assertTrue(stackTrace.contains("java.lang.Exception: Resource Exception"));
        assertTrue(stackTrace.contains("Suppressed: java.lang.Exception: Close Exception"));
    }

    @Test
    public void addingSuppressedExceptionsManually() {
        Exception e = new Exception("Main Exception");
        e.addSuppressed(new Exception("Suppressed 1"));
        e.addSuppressed(new Exception("Suppressed 2"));

        assertEquals(2, e.getSuppressed().length);

        String stackTrace = ExceptionUtils.readStackTrace(e);
        log.debug(stackTrace);

        assertTrue(stackTrace.contains("java.lang.Exception: Main Exception"));
        assertTrue(stackTrace.contains("Suppressed: java.lang.Exception: Suppressed 1"));
        assertTrue(stackTrace.contains("Suppressed: java.lang.Exception: Suppressed 2"));
    }
}
