package com.jvdevlab.java.concurrency;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HandlingInterruptedException {

    // Does it make sense for the method you are implementing to throw an
    // InterruptedException? Put differently, is an InterruptedException a sensible
    // outcome when calling your method?

    // If yes, then throws InterruptedException should be part of your method
    // signature, and you should let the exception propagate (i.e. don't catch it at
    // all).
    @Test
    public void letInterruptedExceptionToPropagate() {
        class A {
            public void method() throws InterruptedException {
                // Server call timed out.
                throw new InterruptedException();
            }
        }

        assertThrows(InterruptedException.class, () -> new A().method());
    }

    // If no, then you should not declare your method with throws
    // InterruptedException and you should (must!) catch the exception. Now two
    // things are important to keep in mind in this situation:

    // 1. Someone interrupted your thread. That someone is probably eager to cancel
    // the operation, terminate the program gracefully, or whatever. You should be
    // polite to that someone and return from your method without further ado.
    @Test
    public void terminateGracefully() {
        class A {

            record Result(String data, boolean isSuccess) {
            }

            public void method() throws InterruptedException {
                // Server call timed out.
                throw new InterruptedException();
            }

            public Result method2() {
                Result result = new Result(null, false);
                try {
                    method();
                    result = new Result("data", true);
                } catch (InterruptedException e) {
                    // log exception
                }
                return result;
            }
        }

        A.Result result = new A().method2();
        if (result.isSuccess) {
            // do something
        } else {
            // recover gracefully
        }
        assertFalse(result.isSuccess);
        assertNull(result.data);
    }

    // 2. Even though your method can manage to produce a sensible return value in
    // case of an InterruptedException the fact that the thread has been interrupted
    // may still be of importance. In particular, the code that calls your method
    // may be interested in whether an interruption occurred during execution of
    // your method. You should therefor log the fact an interruption took place by
    // setting the interrupted flag: Thread.currentThread().interrupt()
    @Test
    public void setTheInterruptFlag() {
        class A {
            public void method() throws InterruptedException {
                // Server call timed out.
                throw new InterruptedException();
            }

            public void method2() {
                try {
                    method();
                } catch (InterruptedException e) {
                    // log exception
                    Thread.currentThread().interrupt();
                }
            }
        }

        assertFalse(Thread.currentThread().isInterrupted());
        new A().method2();
        assertTrue(Thread.currentThread().isInterrupted());
    }

    // By now it should be clear that just doing throw new RuntimeException(e) is a
    // bad idea. It isn't very polite to the caller. You could invent a new runtime
    // exception but the root cause (someone wants the thread to stop execution)
    // might get lost.
    @Test
    public void donNotReThrowInterruptedExceptionAsRuntimeException() {
        class A {
            public void method() throws InterruptedException {
                // Server call timed out.
                throw new InterruptedException();
            }

            public void method2() {
                try {
                    method();
                } catch (InterruptedException e) {
                    // log exception
                    // DON NOT do this.
                    throw new RuntimeException(e);
                }
            }
        }

        assertThrows(RuntimeException.class, () -> new A().method2());
    }

    // You're attempting to read a file and the spec says you should try 10 times
    // with 1 second in between. You call Thread.sleep(1000). So, you need to deal
    // with InterruptedException. For a method such as tryToReadFile it makes
    // perfect sense to say, "If I'm interrupted, I can't complete my action of
    // trying to read the file". In other words, it makes perfect sense for the
    // method to throw InterruptedExceptions.
    @Test
    public void retryLogicWithThreadSleep() {
        class A {
            public void method(int retryCount) throws InterruptedException {
                for (int i = 0; i < retryCount; i++) {
                    // interrupted before all re-tries finished.
                    if (i == retryCount / 2) {
                        throw new InterruptedException();
                    }
                    Thread.sleep(1); // must declare or catch InterruptedException
                }

            }
        }

        assertThrows(InterruptedException.class, () -> new A().method(10));
    }
}
