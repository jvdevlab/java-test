package com.jvdevlab.java.core.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RawType {

    @Test
    public void rawType() {
        List list = new ArrayList();
        list.add(1);
        list.add("a");
        list.add(true);

        assertTrue(list.get(0) instanceof Object);
    }
}
