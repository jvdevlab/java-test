package com.jvdevlab.java.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Diamond {

    @Test
    public void diamond() {

        interface A {
            default String say() {
                return "Hi!";
            }
        }

        interface B {
            default String say() {
                return "Hey!";
            }
        }

        // Duplicate default methods named say with the parameters () and () are
        // inherited from the types B and A
        class C implements A, B {
            public String say() {
                return A.super.say(); // See this fancy stuff
            }
        }

        assertEquals("Hi!", new C().say());
    }

}
