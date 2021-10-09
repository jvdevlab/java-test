package com.jvdevlab.java.API.collections.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.*;

import org.junit.jupiter.api.Test;

import lombok.Data;

public class ImmutableKey {

    @Test
    public void shouldUseImmutableKey() {
        @Data // implements hashCode and equals
        class Employee {
            private String name;
            private String email;
        }

        Map<Employee, String> map = new HashMap<>();
        Employee john = new Employee();
        john.name = "John";
        john.email = "j@j.com";
        map.put(john, "star");

        assertEquals("star", map.get(john));

        // Modified key and know John is lost in the map
        john.email = "jj@j.com";
        assertNull(map.get(john));
    }
}
