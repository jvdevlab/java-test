package com.jvdevlab.java.core.string;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringPool {

    /**
     * All literal strings and string-valued constant expressions are interned.
     * String literals are defined in section 3.10.5 of the The Java™ Language
     * Specification.
     */
    @Test
    public void automaticStringInterning() {
        String a1 = "a";
        String a2 = "a";
        assertTrue(a1 == a2);

        // a3 is not compile time constant
        // so it's not interned automatically
        String a3 = new String("a");
        assertTrue(a3 != a1);
    }

    @Test
    public void explicitStringInterning() {
        String a1 = "a";
        String a2 = new String("a");
        assertTrue(a1 != a2);

        // This alone will not be enough to make a1==a2, as strings are
        // immutable!
        // a2.intern();

        // After interning the object that was created with 'new'
        // become eligible for GC.
        a2 = a2.intern();
        assertTrue(a1 == a2);
    }

}
