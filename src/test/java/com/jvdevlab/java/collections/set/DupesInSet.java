package com.jvdevlab.java.collections.set;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class DupesInSet {

    @Test
    public void doesNotHaveDupes() {
        Set<String> set = new HashSet<>();
        // String implements hashCode and equals
        set.add(new String("a"));
        set.add(new String("a"));
        // No dupes
        assertEquals(1, set.size());
    }

    @Test
    public void dupeGotcha() {
        // Does not implements hashCode and equals
        class BadClass {
            @SuppressWarnings("unused")
            Integer i;

            BadClass(Integer i) {
                this.i = i;
            }
        }

        Set<BadClass> set = new HashSet<>();

        set.add(new BadClass(42));
        set.add(new BadClass(42));
        assertEquals(2, set.size());
    }
}
