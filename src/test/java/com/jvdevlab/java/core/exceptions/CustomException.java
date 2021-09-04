package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CustomException {

    @Test
    public void customCheckedException() {
        @SuppressWarnings("unused")
        class MyException extends Exception {
            private static final long serialVersionUID = -5824921620056524690L;
            private int errorCode = 0;

            // Good to define all combinations for the constructor.
            public MyException(int errorCode) {
                this(errorCode, null, null);
            }

            public MyException(String message) {
                this(0, message, null);
            }

            public MyException(Throwable cause) {
                this(0, null, cause);
            }

            public MyException(int errorCode, String message) {
                this(errorCode, message, null);
            }

            public MyException(int errorCode, Throwable cause) {
                this(errorCode, null, cause);
            }

            public MyException(String message, Throwable cause) {
                this(0, message, cause);
            }

            public MyException(int errorCode, String message, Throwable cause) {
                super(message, cause);
                this.errorCode = errorCode;
            }

            public int getErrorCode() {
                return errorCode;
            }
        }

        assertThrows(MyException.class, () -> {
            throw new MyException(7);
        });
    }

    @Test
    public void customUncheckedException() {
        @SuppressWarnings("unused")
        class MyException extends RuntimeException {
            private static final long serialVersionUID = -5824921620056524690L;
            private int errorCode = 0;

            // Good to define all combinations for the constructor.
            public MyException(int errorCode) {
                this(errorCode, null, null);
            }

            public MyException(String message) {
                this(0, message, null);
            }

            public MyException(Throwable cause) {
                this(0, null, cause);
            }

            public MyException(int errorCode, String message) {
                this(errorCode, message, null);
            }

            public MyException(int errorCode, Throwable cause) {
                this(errorCode, null, cause);
            }

            public MyException(String message, Throwable cause) {
                this(0, message, cause);
            }

            public MyException(int errorCode, String message, Throwable cause) {
                super(message, cause);
                this.errorCode = errorCode;
            }

            public int getErrorCode() {
                return errorCode;
            }
        }

        assertThrows(MyException.class, () -> {
            throw new MyException(7);
        });
    }

}
