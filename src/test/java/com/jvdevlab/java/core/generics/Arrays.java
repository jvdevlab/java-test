package com.jvdevlab.java.core.generics;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class Arrays {

    @Test
    public void canNotHaveGenericArray() {
        // Error: The type of the expression must be an array type but it
        // resolved to ArrayList<Object>
        // List<String>[] array = new ArrayList<>()[10];
        // https://stackoverflow.com/questions/7810074/array-of-generic-list
        // "You can't have an array, because an array requires a raw type."

        // Warning: Type safety: The expression of type ArrayList[] needs
        // unchecked conversion to conform to List<String>
        @SuppressWarnings("unchecked")
        List<String>[] array = new ArrayList[10];

        // Runtime exception: java.lang.ArrayStoreException:java.util.LinkedList
        // array[0] = new LinkedList<>();
        array[0] = new ArrayList<>();
        // must be string
        // array[0].add(true);
        array[0].add("string");
        assertNotNull(array[0]);

        // Better create List of Lists
        List<List<String>> list = new ArrayList<>();
        list.add(new ArrayList<>());
        assertNotNull(list.get(0));
    }
}
