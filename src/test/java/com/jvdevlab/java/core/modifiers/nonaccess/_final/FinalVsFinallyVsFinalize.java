package com.jvdevlab.java.core.modifiers.nonaccess._final;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import com.jvdevlab.java.core.exceptions.TryCatchFinallyAndThrow;

import org.junit.jupiter.api.Test;

public class FinalVsFinallyVsFinalize {

    /**
     * Finalize
     */

    @Test
    public void finalizeIsDeprecated() throws Exception {
        // From JavaDocs:
        // The finalization mechanism is inherently problematic.
        // Finalization can lead to performance issues, deadlocks, and hangs.
        // Errors in finalizer can lead to resource leaks; there is no way to cancel
        // finalization if it is no longer necessary; and no ordering is specified
        // among calls to {@code finalize} methods of different objects.
        // Furthermore, there are no guarantees regarding the timing of finalization.
        // The {@code finalize} method might be called on a finalizable object
        // only after an indefinite delay, if at all.

        // Classes whose instances hold non-heap resources should provide a method
        // to enable explicit release of those resources, and they should also
        // implement {@link AutoCloseable} if appropriate.
        // The {@link java.lang.ref.Cleaner} and {@link java.lang.ref.PhantomReference}
        // provide more flexible and efficient ways to release resources when an object
        // becomes unreachable.

        Method method = Object.class.getDeclaredMethod("finalize");
        Deprecated deprecated = method.getAnnotation(Deprecated.class);
        // Test will pass only on Java 9 and above.
        assertEquals("9", deprecated.since());
    }

    /**
     * Finally
     */

    @Test
    public void finallyExecutesEvenIfThereIsAnException() {
        new TryCatchFinallyAndThrow().finallyBlockAlmostAlwaysExecutes();
    }

    @Test
    public void finallyMightNotBeNeeded() {
        new TryCatchFinallyAndThrow().finallyMightNotBeNeeded();
    }

    /**
     * Final
     */

    @SuppressWarnings("unused")
    @Test
    public void finalUseCases() {
        final class A {
        }
        // The type B cannot subclass the final class A
        // class B extends A{ }

        class C {
            // The blank final field field may not have been initializedJava
            // final int field;
            final int field = 0;

            final void method() {
            }

            void method(final int arg) {
                // The final local variable arg cannot be assigned. It must be blank and not
                // using a compound assignment
                // arg = 0;

                final int local;
                local = 0;
                // The final local variable local may already have been assignedJava
                // local = 1;
            }
        }

        C classC = new C();
        // The final field C.field cannot be assigned
        // classC.field = 1;

        class D extends C {
            // Cannot override the final method from C
            // void method() {}
        }
    }
}
