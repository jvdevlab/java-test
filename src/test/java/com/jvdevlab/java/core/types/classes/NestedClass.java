package com.jvdevlab.java.core.types.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        @SuppressWarnings("unused")
        class Outer {
            private int outer = 1;

            static class Inner {
                private int inner;

                Inner(int inner) {
                    // has it's own this
                    this.inner = inner;
                }

                int getInner() {
                    return this.inner;
                }

                /*
                 * Cannot make a static reference to the non-static field outer"
                 * int getOuterInstanceVariable() { return outer; }
                 */
            }
        }

        // you can create many instance of a static inner classes
        // it's not like static variable that becomes global.
        Outer.Inner inner = new Outer.Inner(2);
        Outer.Inner anotherInner = new Outer.Inner(3);
        assertEquals(2, inner.getInner());
        assertEquals(3, anotherInner.getInner());
    }
}
