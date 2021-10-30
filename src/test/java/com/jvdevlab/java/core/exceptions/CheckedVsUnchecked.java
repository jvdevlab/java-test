package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import com.jvdevlab.java.functional.lambda.ExceptionDonNotPropagateInStreams;

import org.junit.jupiter.api.Test;

// "Checked exceptions is a somewhat controversial feature that forces programmers
// to acknowledge the fact that an exception may be thrown, either by catching it
// or by explicitly allowing it to propagate up the call stack. The creators of C#
// chose to leave this feature out, while many Java supporters argue that it's a
// useful feature contributing to more resilient software."
public class CheckedVsUnchecked {

    // "Checked exceptions are often used as "alternative return values" for
    // unpredictable errors from which client can recover. "
    @Test
    public void checkedExceptionMustBeEitherCaughtOrDeclaredToBeThrown() {
        @SuppressWarnings("unused")
        class A {
            public void method() {
                // Compiler error: Unhandled exception type Exception
                // throw new Exception();
            }

            public void method2() throws Exception {
                throw new Exception();
            }

            public void method3() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    // do nothing;
                }
            }
        }

        assertThrows(Exception.class, () -> new A().method2());
    }

    // "Unchecked exceptions are usually an indication of a programming error or
    // other condition from which the client can't be expected to recover."
    @Test
    public void uncheckedExceptionDoesNotHaveThisRequirement() {
        class A {
            public void method() {
                throw new RuntimeException();
            }
        }

        assertThrows(RuntimeException.class, () -> new A().method());
    }

    // A situation is not exceptional just because it's less common than other
    // situations. In a non-exceptional situation you should use return values
    // instead of exceptions.
    @Test
    public void useCustomReturnValuesIfSituationIsNotExceptional() {
        class A {
            record Result(String message, boolean isSuccess) {
            }

            public Result method() {
                return new Result("Product unavailable.", false);
            }
        }

        A.Result result = new A().method();
        assertFalse(result.isSuccess);
        assertEquals("Product unavailable.", result.message);
    }

    @Test
    @SuppressWarnings("null")
    public void doNotHandleRuntimeExceptions() {
        // they are most likely bugs
       
        assertThrows(ArithmeticException.class, () -> {
            @SuppressWarnings("unused")
            int i = 1 / 0;
        });

        assertThrows(NullPointerException.class, () -> {
            String s = null;
            s.length();
        });

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            String[] s = new String[0];
            s[1] = "s";
        });
    }

    @Test
    public void doNotHandleErrors() {
        // You can't recover from them.

        assertThrows(OutOfMemoryError.class, () -> {
            throw new OutOfMemoryError();
        });

        assertThrows(StackOverflowError.class, () -> {
            throw new StackOverflowError();
        });

        assertThrows(NoSuchMethodError.class, () -> {
            throw new NoSuchMethodError();
        });
    }

    @Test
    public void doHandleRecoverableExceptions() {
        try (FileReader reader = new FileReader("@")) {
            reader.read();
        } catch (IOException e) {
            // Run code that displays error message to the user.
        }

        try {
            // Make a db call
            throw new SQLException();
        } catch (SQLException e) {
            // Run code that retries or does exponential back-off.
        }
    }

    @Test
    public void throwableIsRegardedAsACheckedException() {
        // Compiler error: Unhandled exception type Throwable
        // throw new Throwable();
    }

    @Test
    public void exceptionsExceptRuntimeExceptionAreRegardedAsACheckedException() {
        // Compiler error: Unhandled exception type Exception
        // throw new Exception();
    }

    @Test
    public void runtimeExceptionIsSubclassOfExceptionButItIsSpecailBecuaseItIsUnheckedException() {
        assertTrue(new RuntimeException() instanceof Exception);

        assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void errorsAreRegardedAsAUncheckedExceptions() {
        assertThrows(OutOfMemoryError.class, () -> {
            throw new OutOfMemoryError();
        });
    }

    // "So, checked exceptions are hard get right, and misuse has problematic
    // consequences. It should be noted however, that this is the case with all
    // non-trivial language features. There are many examples of abstract
    // classes
    // that should have been interfaces, and many designs based on inheritance
    // where
    // aggregation would have been better."

    @Test
    @SuppressWarnings("unused")
    public void checkedExceptionProsFailFastDevelopment() {
        // "Exceptional situations, such as possible IO errors, surface at
        // compile
        // time."
        class A {
            public void method() throws IOException {
            }
        }

        A a = new A();
        // Compiler Error: Unhandled exception type IOException
        // a.method();
    }

    @Test
    public void checkedExceptionProsContract() {
        // "The fact that a method throws a certain exception can be an
        // essential part
        // of the contract. The throws declaration is therefore as important as
        // the
        // types of the return value and parameters. Apart from the static
        // analysis it
        // enables, it also serves a documentation purpose for the programmer."
    }

    @Test
    public void checkedExceptionConsExtensibility() {
        // "You can't add throws SomeCheckedException to a method signature
        // without
        // breaking client code, which restricts the evolution of APIs."
    }

    @Test
    public void checkedExceptionConsMisuse() {
        // "Many checked exceptions, including some in the the Java API, should
        // have
        // been implemented as unchecked exceptions. Such misuse leads to
        // frustration
        // and in the end catch blocks that either ignores them or rethrows them
        // as
        // RuntimeExceptions which in turn are never caught."

        // Compiler error: Unhandled exception type MalformedURLException
        // Even though the error will never be thrown in the below case.
        // URL url = new URL("https://google.com");

        // Many developers will rethrow this as RuntimeException to simplify
        // things.
        class URLUtils {
            public URL createUrl(String url) {
                URL result = null;
                try {
                    result = new URL(url);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                return result;
            }
        }

        assertNotNull(new URLUtils().createUrl("https://google.com"));

        // Spring Framework tend to re-throw most of checked exceptions,
        // including the ones you that you suppose to be able to recover like
        // SQLException
    }

    @Test
    public void checkedExceptionConsPropagation() {
        new ExceptionDonNotPropagateInStreams()
                .exceptionDonNotPropagateInStreams();
    }
}
