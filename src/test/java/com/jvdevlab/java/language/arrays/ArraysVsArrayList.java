package com.jvdevlab.java.language.arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ArraysVsArrayList {

    @Test
    public void arrayVsArrayListPerformance() {
        // You might want to use Array vs ArrayList for performance reasons
        // as it holds primitives. This might change: JEP 218: Generics over
        // Primitive Types. See: https://openjdk.java.net/jeps/218

        // int size = 1000000;
        // Speed it up
        int size = 100;

        long before = System.currentTimeMillis();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i++;
        }
        long timeUsedByArray = System.currentTimeMillis() - before;
        log.debug("Time it takes to fill in an Array: " + timeUsedByArray);

        before = System.currentTimeMillis();
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(i++); // i is autoboxed into Integer.
        }
        long timeForArrayList = System.currentTimeMillis() - before;
        log.debug("Time it takes to fill in an ArrayList: " + timeForArrayList);

        assertTrue(timeUsedByArray <= timeForArrayList);
    }

    @Test
    public void arrayVsArrayListResizing() {
        // You can grow and shrink an ArrayList.
        List<Integer> list = new ArrayList<>();
        Integer i = 1;
        list.add(i);
        list.remove(i);
        assertEquals(0, list.size());

        // But, you can't grow and shrink an Array
        Integer[] orig = new Integer[] {};
        List<Integer> listBackedByArray = Arrays.asList(orig);
        assertThrows(UnsupportedOperationException.class, () -> {
            listBackedByArray.add(i);
        });

        // Still, you can create a new Array using Apache Commons
        // or Arrays.copyOf() or System.arraycopy()
        Integer[] modified = ArrayUtils.add(orig, i);
        assertNotEquals(orig, modified);
    }

    @Test
    public void arrayVsListImmutability() {
        // For Arrays, only reference can be made immutable.
        final int[] array = { 1 };
        // This will give compilation error:
        // array = new int[] { 2 };
        // "The final local variable array cannot be assigned. It must be blank and not
        // using a compound assignment"
        // However, elements of the array are NOT immutable.
        array[0] = 2;
        assertEquals(2, array[0]);

        // For List, both the reference and elements can be made immutable.
        final List<Integer> list = Collections.unmodifiableList(Arrays.asList(1));
        assertThrows(UnsupportedOperationException.class, () -> {
            list.set(0, 2);
        });
    }

    @Test
    public void haveToUseArrayForAPIThatRequiresIt() {
        class EchoAPI {
            int[] echo(int[] array) {
                return array;
            };
        }

        // In most cases prefer List to Array, but sometimes you need to use array.
        List<Integer> listInput = new ArrayList<>();
        listInput.add(1);
        listInput.add(2);
        listInput.add(3);

        // Convert a List of references to an Array of primitives
        int[] arrayInput = listInput.stream().mapToInt(i -> i).toArray();

        // Call an API that requires Array.
        int[] arrayOutput = new EchoAPI().echo(arrayInput);

        // Convert Array back to List.
        // This will give compilation error:
        // List<Integer> listOutput = Arrays.asList(arrayOutput);
        // "Type mismatch: cannot convert from List<int[]> to List<Integer>"
        List<Integer> listOutput = Arrays.stream(arrayOutput).boxed().collect(Collectors.toList());

        assertEquals(listInput.size(), listOutput.size());
    }

    @Test
    public void arrayBackedList() {
        Integer[] array = { 1, 2 }; // autoboxing
        List<Integer> arrayBackedList = Arrays.asList(array);

        // Now, you can use List's methods that will affect original array.
        arrayBackedList.replaceAll(i -> i * i);
        assertEquals(1, array[0]);
        assertEquals(4, array[1]);
        assertEquals(1, arrayBackedList.get(0));
        assertEquals(4, arrayBackedList.get(1));

        // And as such, you still can't modify the size of the array.
        assertThrows(UnsupportedOperationException.class, () -> {
            arrayBackedList.remove(0);
        });
    }

}
