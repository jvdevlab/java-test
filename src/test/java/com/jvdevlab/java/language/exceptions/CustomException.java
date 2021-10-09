package com.jvdevlab.java.language.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import lombok.Data;

public class CustomException {

    @Test
    public void customCheckedException() {
        @Data
        class MyException extends Exception {
            private int customErrorCode;

            MyException(int customErrorCode) {
                this.customErrorCode = customErrorCode;
            }
        }

        try {
            throw new MyException(42);
        } catch (MyException e) {
            assertEquals(42, e.customErrorCode);
        }
    }

    @Test
    public void customUncheckedException() {
        @Data
        class MyException extends RuntimeException {
            private int customErrorCode;

            MyException(int customErrorCode) {
                this.customErrorCode = customErrorCode;
            }
        }

        assertThrows(MyException.class, () -> {
            throw new MyException(42);
        });
    }

}
