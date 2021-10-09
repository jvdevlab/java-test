package com.jvdevlab.java.language.primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * @see AutoboxingVsUnboxing
 */
public class WrapperClasses {

    @Test
    public void wrappersAreCaching() {
        // The valueOf methods of Number wrapper classes cache
        // the following range −128 and 127
        assertTrue(Integer.valueOf(100) == Integer.valueOf(100));
        assertTrue(Integer.valueOf(1000) != Integer.valueOf(1000));
    }

    @Test
    public void wrappersAreImmutable() {
        Integer iObject = 1;
        Integer iObject2 = iObject++; // unboxed > boxed;
        // 2 different references;
        assertTrue(iObject != iObject2);
    }

    @Test
    public void numbers() {
        Byte b = 1;
        Short s = 1;
        Integer i = 1;
        Long l = 1l;
        Float f = 1f;
        Double d = 1d;
        assertTrue(b instanceof Number);
        assertTrue(s instanceof Number);
        assertTrue(i instanceof Number);
        assertTrue(l instanceof Number);
        assertTrue(f instanceof Number);
        assertTrue(d instanceof Number);
    }

    @Test
    public void nullability() {
        Integer iObject = null;
        // "Type mismatch: cannot convert from null to int"
        // int i = null;
        assertNull(iObject);
    }

    @Test
    public void generics() {
        List<Integer> integers = new ArrayList<>();
        // "Syntax error, insert "Dimensions" to complete ReferenceType"
        // Maybe supported in future Java versions.
        // See JEP 218: Generics over Primitive Types.
        // List<int> ints = new ArrayList<>();
        assertTrue(integers.size() == 0);
    }

    @Test
    public void synchronization() {
        int i = 1;
        // "int is not a valid type's argument for the synchronized statement"
        /*
         * synchronized (i) { // do nothing }
         */

        /*
         * Actually, wrapper classes also shouldn't be used this way. See compiler
         * warning:
         * "Integer is a value-based type which is a discouraged argument for the synchronized statement"
         */
        Integer iObject = 1;
        /*
         * synchronized (iObject) { // do nothing }
         */
        assertEquals(i, iObject);
    }
}
