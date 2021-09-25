package com.jvdevlab.java.core.arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Arrays {

    @Test
    public void theBuiltInTypeArrayIsAnObject() {
        assertTrue(new int[] {} instanceof Object);
    }

    @Test
    public void differentWaysToInitAnArray() {
        // specify capacity
        int[] array = new int[3];
        // inline initialization
        int[] array2 = new int[] { 1, 2, 3 };
        // short syntax, w/o new keyword
        int[] array3 = { 1, 2, 3 };
        // "Array constants can only be used in initializers"
        // array = {1, 2, 3}
        // this syntax is rarely used.
        int array4[] = { 1, 2, 3 };

        assertEquals(3, array.length);
        assertEquals(3, array2.length);
        assertEquals(3, array3.length);
        assertEquals(3, array4.length);
    }

    @Test
    public void arrayBounds() {
        int[] array = { 1, 2, 3 };

        // Array index starts from 0 and ends with array.length-1
        assertEquals(1, array[0]);
        assertEquals(3, array[array.length - 1]);

        // You can't access element with an index that is outside array bounds.

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            array[array.length] = 0;
        });

        // Max Array size is Integer.MAX_VALUE.
        assertThrows(OutOfMemoryError.class, () -> {
            // But most likely you will get:
            // "java.lang.OutOfMemoryError: Requested array size exceeds VM
            // limit"
            int[] largestArray = new int[Integer.MAX_VALUE];
            largestArray[0] = 1;
            // This is platform specific and you might be able to create an
            // array
            // with Integer.MAX_VALUE-1 or Integer.MAX_VALUE-2 or other smaller
            // number.
            // Practically speaking, you'd be better of with using smaller
            // arrays.
        });
    }

    @Test
    public void arraysOfArrays() {
        int[][] twoByTwo = { { 0, 1 }, { 2, 3 } };
        assertEquals(0, twoByTwo[0][0]);
        assertEquals(3, twoByTwo[1][1]);

        int[][] threeByThree = new int[3][3];
        // You can make this array asymmetrical
        threeByThree[1] = new int[2]; // the middle row has only 2 columns now
        assertNotEquals(threeByThree[0].length, threeByThree[1].length);
        assertEquals(threeByThree[0].length, threeByThree[2].length);
    }

}
