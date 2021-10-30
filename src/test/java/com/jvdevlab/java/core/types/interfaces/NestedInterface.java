package com.jvdevlab.java.core.types.interfaces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NestedInterface {

    @Test
    public void nestedInterface() {
        interface I {
            interface Y {
                int someValue();
            }
        }

        class C implements I.Y {
            @Override
            public int someValue() {
                return 42;
            }
        }

        assertEquals(42, new C().someValue());
    }

    @Test
    public void nestedClassInsideInterface() {
        interface I {
            class Y {
                int someValue() {
                    return 42;
                };
            }
        }

        assertEquals(42, new I.Y().someValue());
    }
}