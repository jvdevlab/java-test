package com.jvdevlab.java.functional.lambda.functionalinterface;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LambdaThingsToNote {

    @Test
    public void abstractClassCanNotBeFunctionalInterface() {
        abstract class AbstractClassA {
            abstract boolean doStuff();
        }

        @SuppressWarnings("unused")
        class ClassA {
            public boolean flag = false;

            public ClassA(AbstractClassA a) {
                flag = a.doStuff();
            }
        }

        // Compilation error: "The target type of this expression must be a functional
        // interface"
        // ClassA a = new ClassA(() -> 1 < 2);
    }

    interface StillAFunctionalInterface {
        boolean doStuff();

        default boolean doNewStuff() {
            return false;
        }
    }

    @Test
    public void functionalInterfaceWithDefaultMethod() {
        StillAFunctionalInterface i = () -> 1 < 2;
        assertTrue(i.doStuff());
        assertFalse(i.doNewStuff());
    }

    @FunctionalInterface
    interface GuaranteedFunctionalInterface {
        boolean doStuff();

        // Compile error: "Invalid '@FunctionalInterface' annotation;
        // LambdaThingsToNote.GuranteedFunctionalInterface is not a functional
        // interface"
        // boolean doNewStuff();
    }
}
