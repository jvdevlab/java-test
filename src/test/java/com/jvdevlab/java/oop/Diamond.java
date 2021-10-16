package com.jvdevlab.java.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Diamond {

    @Test
    public void diamond() {

        interface B {
            default String say() {
                return "Hi!";
            }
        }

        interface C {
            @SuppressWarnings("unused")
            default String say() {
                return "Hey!";
            }
        }

        // Which say() method is inherited? From B or C?
        // Compiler will make you provide an implementation of
        // this method inside D to resolve the conflict.
        class D implements B, C {
            public String say() {
                // Whit this syntax you can redirect implementation
                // to a specific interface
                return B.super.say();
            }
        }

        assertEquals("Hi!", new D().say());
    }

}
