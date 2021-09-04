package com.jvdevlab.java.collections.set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class HashSetTest {

    @Test
    public void hashSet() {
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");

        // no dupes
    }

    @Test
    public void cantHaveDupes() {
        Set<Integer> set = new HashSet<>();

        // 2 different instances
        Integer i1 = Integer.valueOf(777);
        Integer i2 = Integer.valueOf(777);
        assertFalse(i1 == i2);

        // No dupes
        set.add(i1);
        set.add(i2);
        assertEquals(1, set.size());
    }

    @Test
    public void dupeGotcha() {

        class BadClass {
            @SuppressWarnings("unused")
            Integer i;

            BadClass(Integer i) {
                this.i = i;
            }
        }

        Set<BadClass> set = new HashSet<>();
        Integer i = Integer.valueOf(777);

        set.add(new BadClass(i));
        set.add(new BadClass(i));
        assertEquals(2, set.size());
    }

}
