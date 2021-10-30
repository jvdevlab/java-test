package com.jvdevlab.java.core.types.records;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Preview since Java 14
public class Records {

    @Test
    public void recordGetters() {
        record Person(String firstName, String lastName) implements Cloneable {
        }

        // free constructor
        Person p = new Person("James", "Bond");

        // free accessors
        assertEquals("James", p.firstName());
        assertEquals("Bond", p.lastName());

        // free equals() and hashCode()
        Person p2 = new Person("James", "Bond");
        assertTrue(p.equals(p2));
        assertEquals(p.hashCode(), p2.hashCode());

        // free toString
        assertEquals("Person[firstName=James, lastName=Bond]", p.toString());

        // can implement interface but not extend classes
        assertTrue(p instanceof Cloneable);
    }

}
