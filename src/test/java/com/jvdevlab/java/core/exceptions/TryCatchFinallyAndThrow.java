package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ExceptionUtils;

public class TryCatchFinallyAndThrow {

    /**
     * Throw
     */

    class A {
        public void method3() {
            throw new RuntimeException();
        }

        public void method2() {
            method3();
        }
    }

    class B {
        public void method1() {
            new A().method2();
        }
    }

    @Test
    public void exceptionsPropogationAccrossMethodsAndClasses() {
        assertThrows(RuntimeException.class, () -> new B().method1());
    }

    @SuppressWarnings("null")
    @Test
    public void exceptionThrownByTheAPICall() {
        assertThrows(NullPointerException.class, () -> {
            String s = null;
            s.length();
        });
    }

    /**
     * Try, Catch
     */

    @Test
    public void multipleCatchesInheretenceVsUnrelatedExceptionHandling() {
        // For demo purposes only. We shouldn't catch runtime exceptions.

        boolean mostNarrowExceptionWins = false;

        try {
            throw new ArrayIndexOutOfBoundsException();
        } catch (ArrayIndexOutOfBoundsException e) {
            mostNarrowExceptionWins = true;
        } catch (ClassCastException e) {
            // won't get here at all
        } catch (IndexOutOfBoundsException e) {
            // only gets here if ArrayIndexOutOfBoundsException is not caught
        } catch (RuntimeException e) {
            // only gets here if IndexOutOfBoundsException is not caught
        } catch (Exception e) {
            // only gets here if RuntimeException is not caught
        }

        assertTrue(mostNarrowExceptionWins);
    }

    /**
     * Try, Finally
     */

    @Test
    public void finallyBlockAlmostAlwaysExecutes() {
        AtomicBoolean isFinallyExecuted = new AtomicBoolean(false);

        assertThrows(RuntimeException.class, () -> {
            try {
                throw new RuntimeException();
            } finally {
                // "The only way to prevent a finally block from running is by terminating the
                // VM through System.exit or killing it manually."
                isFinallyExecuted.set(true);
            }
        });

        assertTrue(isFinallyExecuted.get());
    }

    @Test
    public void finallyMightNotBeNeeded() {
        // finally block was mostly used to close resources (file, db, network, etc.)
        // The new try-with-resources constrict takes care of this most popular
        // use-case. As such, the need for finally becomes limited.

        new TryWithResources().newWayOfClosingAResourceImplicitly();
    }

    @Test
    public void finallyExecutesEvenIfReturnFromTryBlock() {
        class ClassA {
            @SuppressWarnings("finally")
            public boolean method() {
                try {
                    return false;
                } finally {
                    // for demo purposes only, don't do this in real life!
                    return true;
                }
            }
        }

        // Finally overrides the result value.
        assertTrue(new ClassA().method());
    }

    @Test
    @SuppressWarnings("finally")
    public void discardedException() {
        String stackTrace = "";
        try {
            try {
                throw new Exception("Discarded");
            } finally {
                // for demo purposes only, don't do this in real life!
                throw new Exception("Wins");
            }
        } catch (Exception e) {
            stackTrace = ExceptionUtils.readStackTrace(e);
            // The Discarded exception is not added to the Suppressed exceptions!
            assertEquals(0, e.getSuppressed().length);
        }

        assertTrue(stackTrace.contains("Wins"));
        assertFalse(stackTrace.contains("Discarded"));
    }

    @Test
    public void omittingCatch() {
        // Exception propagates
        assertThrows(Exception.class, () -> {
            try {
                throw new Exception();
            } finally {
                // do nothing
            }
        });
    }

}
