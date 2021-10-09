package com.jvdevlab.java.language.exceptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChainedExceptions {

    @Test
    public void chainedExceptions() {
        String stackTrace = "";

        try {
            try {
                throw new SQLException("SQL Exception");
            } catch (SQLException e) {
                throw new Exception("Internal Server Error Exception", e); // chained
            }
        } catch (Exception e) {
            stackTrace = ExceptionUtils.readStackTrace(e);
        }

        assertTrue(stackTrace.contains("java.lang.Exception: Internal Server Error Exception"));
        assertTrue(stackTrace.contains("Caused by: java.sql.SQLException: SQL Exception"));
        log.debug(stackTrace);
    }

}
