package com.jvdevlab.java.API.collections.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class NotHashCodeOrEquals {
    
    @Test
    public void keyDoesNotImplementingHashCode() {
        class Entry {
            String name;

            Entry(String name) {
                this.name = name;
            }

            @Override
            public boolean equals(Object o) {
                if (o == null)
                    return false;
                if (this == o)
                    return true;
                if (o instanceof Entry eo) {
                    return Objects.equals(this.name, eo.name);
                } else {
                    return false;
                }
            }
        }

        Map<Entry, String> map = new HashMap<>();
        map.put(new Entry("JV"), "Lab");
        map.put(new Entry("JV"), "Lab");

        // has dupes
        assertEquals(2, map.size());
    }

    @Test
    public void keyDoesNotImplementingEquals() {
        class Entry {
            String name;

            Entry(String name) {
                this.name = name;
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(this.name);
            }
        }

        Map<Entry, String> map = new HashMap<>();
        map.put(new Entry("JV"), "Lab");
        map.put(new Entry("JV"), "Lab");

        // has dupes
        assertEquals(2, map.size());
    }

    @Test
    public void keyImplementsHashCodeAndEquals() {
        class Entry {
            String name;

            Entry(String name) {
                this.name = name;
            }

            @Override
            public boolean equals(Object o) {
                if (o == null)
                    return false;
                if (this == o)
                    return true;
                if (o instanceof Entry eo) {
                    return Objects.equals(this.name, eo.name);
                } else {
                    return false;
                }
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(this.name);
            }
        }

        Map<Entry, String> map = new HashMap<>();
        map.put(new Entry("JV"), "Lab");
        map.put(new Entry("JV"), "Lab");

        assertEquals(1, map.size());
    }

}
