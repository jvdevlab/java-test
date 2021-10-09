package com.jvdevlab.java.language.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Bitwise {

    @Test
    public void twosCompliment() {
        int a = 0b00011100; // 28
        assertEquals(28, a);

        // First, we need to compliment/flip/invert each bit
        int b = 0b11100011;
        // Second, we need to add 1.
        int one = 0b0000001;
    }

    @Test
    public void and() {
        int a = 60;
        int b = 13;
        assertEquals(12, a & b);
    }

    @Test
    public void leftShift() {
        int a = 60;
        assertEquals(240, a << 2);
    }

    @Test
    public void rightShift() {
        int a = 60;
        int b = 2;
        int x = a >> b;
        assertEquals(15, x);
        assertEquals(a / Math.pow(2, b), x);
        assertEquals("1111", Integer.toBinaryString(x));
    }

    @Test
    public void zeroFillRightShift() {
        int a = 60;
        int b = 2;
        int x = a >>> b;
        assertEquals(15, x);
        assertEquals(a / Math.pow(2, b), x);
        assertEquals("00001111",
                ("%32s%n".formatted(Integer.toBinaryString(x))));
    }

    @Test
    public void xor() {
        int a = 60;
        int b = 13;
        assertEquals(49, a ^ b);
    }

    @Test
    public void or() {
        int a = 60;
        int b = 13;
        assertEquals(61, a | b);
    }

    @Test
    public void compliment() {
        /*
         * int a = 0b1010; // the first confusion is that when we say above it
         * rally means a = 0b00000000000000000000000000001010; // 32bits
         * assertEquals(10, a);
         * 
         * // Why it equals -11 assertEquals(-11, ~a);
         * assertEquals("11111111111111111111111111110101",
         * Integer.toBinaryString(~a)); // from normal binary to decimal
         * conversion we would get 4294967285 // but "This is the one’s
         * complement of the decimal number 10. And since // the first
         * (leftmost) bit is 1 in binary, ***it means that the sign is //
         * negative*** for the number that is stored. Now, since the numbers are
         * // stored as 2’s complement, first we need to find its 2’s complement
         * // and then convert the resultant binary number into a decimal
         * number"
         * 
         * // 1. flip all bytes int c = 0b11111111111111111111111111110101;
         * assertEquals("1010", Integer.toBinaryString(~c)); // 2. add 1 // 1010
         * // + // 0001 // ----- // 1011 = -11 c = c + 1; // assertEquals(-11,
         * ~c);
         */

        int a = 0b1010;
        assertEquals("00001111", ("%32s"
                .formatted(Integer.toBinaryString(a).replace(' ', '0'))));
        int b = 0b0101;
        int c = 0b0001;

        assertEquals(~a, b + b + c);
    }
}