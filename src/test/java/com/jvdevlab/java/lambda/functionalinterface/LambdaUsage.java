package com.jvdevlab.java.lambda.functionalinterface;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LambdaUsage {

    // "Functional interfaces are interfaces with a single method."
    interface InterfaceA {
        boolean doStuff();
    }

    @Test
    public void lambdaAsInstance() {
        InterfaceA a = () -> 1 < 2;
        assertTrue(a.doStuff());
    }

    @Test
    public void lambdaAsMethodArgument() {
        class ClassA {
            public boolean method(InterfaceA a) {
                return a.doStuff();
            }
        }

        assertTrue(new ClassA().method(() -> 1 < 2));
    }

    @Test
    public void lambdaAsConstructorArgument() {
        class ClassA {
            public boolean flag = false;

            public ClassA(InterfaceA a) {
                flag = a.doStuff();
            }
        }

        ClassA a = new ClassA(() -> 1 < 2);
        assertTrue(a.flag);
    }

}
