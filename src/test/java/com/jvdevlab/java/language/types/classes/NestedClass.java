package com.jvdevlab.java.language.types.classes;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class NestedClass {

    @Test
    public void instanceOfAnInnerClass() {
        class Outer {
            class Inner {
            }
        }

        // have to create outer first and then used .new
        Outer.Inner inner = new Outer().new Inner();
        assertNotNull(inner);
    }

    @Test
    public void instanceOfAnStaticNestedClass() {
        class Outer {
            static class Inner {
            }
        }

        Outer.Inner inner = new Outer.Inner();
        assertNotNull(inner);
    }
}
