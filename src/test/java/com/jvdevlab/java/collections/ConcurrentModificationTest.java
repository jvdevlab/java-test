package com.jvdevlab.java.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.junit.jupiter.api.Test;

public class ConcurrentModificationTest {

    /**
     * From JavaDoc:
     * 
     * Note that the fail-fast behavior of an iterator cannot be guaranteed as it
     * is, generally speaking, impossible to make any hard guarantees in the
     * presence of unsynchronized concurrent modification. Fail-fast iterators throw
     * {@code ConcurrentModificationException} on a best-effort basis. Therefore, it
     * would be wrong to write a program that depended on this exception for its
     * correctness: <i>the fail-fast behavior of iterators should be used only to
     * detect bugs.</i>
     * 
     */
    @Test
    public void concurrentModification() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // prehistoric
        assertThrows(ConcurrentModificationException.class, () -> {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String e = it.next();
                list.remove(e);
            }
        });

        // old school
        assertThrows(ConcurrentModificationException.class, () -> {
            for (String e : list) {
                list.add(e + "_mode");
            }
        });

        // new kid
        assertThrows(ConcurrentModificationException.class, () -> {
            list.forEach((e) -> {
                list.add(e + "_mod");
            });
        });

        // same for remove
        assertThrows(ConcurrentModificationException.class, () -> {
            list.forEach((e) -> {
                list.remove(e);
            });
        });

    }

    @Test
    public void noneStructuralChangeIsOK() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // set will work as we don't "modifies the list structurally"
        list.forEach(e -> {
            list.set(0, "mod");
        });
        assertEquals("mod", list.get(0));
    }

    @Test
    public void concurrentChangeUsingIteratorMethodsIsOK() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // modify using Iterator (has only remove) and ListIterator
        ListIterator<String> it = list.listIterator();
        while (it.hasNext()) {
            String e = it.next();
            if ("b".equals(e)) {
                it.remove();
                it.add("b_mod");
            }
        }

        assertFalse(list.contains("b"));
        assertTrue(list.contains("b_mod"));
    }

}
