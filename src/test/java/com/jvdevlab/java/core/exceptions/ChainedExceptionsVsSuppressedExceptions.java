package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ExceptionUtils;

public class ChainedExceptionsVsSuppressedExceptions {

    @Test
    public void chainedExceptions() {
        String stackTrace = "";

        try {
            try {
                throw new SQLException("Level 1");
            } catch (SQLException e) {
                throw new Exception("Level 2", e); // chained
            }
        } catch (Exception e) {
            stackTrace = ExceptionUtils.readStackTrace(e);
        }

        assertTrue(stackTrace.contains("java.lang.Exception: Level 2"));
        assertTrue(stackTrace.contains("Caused by: java.sql.SQLException: Level 1"));
    }

    @Test
    public void suppressedExceptionsGeneratedByTryWithResources() {
        new TryWithResources().suppressedExceptions();
    }

    @Test
    public void addingSuppressedExceptionsManually() {
        Exception e = new Exception("Main Exception");
        e.addSuppressed(new Exception("Suppressed 1"));
        e.addSuppressed(new Exception("Suppressed 2"));

        assertEquals(2, e.getSuppressed().length);

        String stackTrace = ExceptionUtils.readStackTrace(e);

        assertTrue(stackTrace.contains("java.lang.Exception: Main Exception"));
        assertTrue(stackTrace.contains("Suppressed: java.lang.Exception: Suppressed 1"));
        assertTrue(stackTrace.contains("Suppressed: java.lang.Exception: Suppressed 2"));
    }
}
