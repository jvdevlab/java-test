package com.jvdevlab.java.core.modifiers.others._static;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InnerClass {

    int member = 0;

    @Test
    public void staticNestedClass() {
        class MyInnerClass {
            int field = 0;

            public int getField() {
                return this.field;
            }

            public int getMember() {
                return member; // not via this
            }
        }

        MyInnerClass myClass = new MyInnerClass();
        assertEquals(0, myClass.getField());
        assertEquals(0, myClass.getMember());
    }
}
