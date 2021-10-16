package com.jvdevlab.java.language.operators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Bitwise {

    private String getBinaryString(int i) {
        return String.format("%8s", Integer.toBinaryString(i)).replace(' ',
                '0');
    }

    @Test
    public void and() {
        int a = 60;
        int b = 13;
        assertEquals(12, a & b);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        assertEquals("00001101", getBinaryString(b)); // 0+0+0+0+8+4+0+1=13
        assertEquals("00001100", getBinaryString(a & b)); // 0+0+0+0+8+4+0+0=12
    }

    @Test
    public void or() {
        int a = 60;
        int b = 13;
        assertEquals(61, a | b);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        assertEquals("00001101", getBinaryString(b)); // 0+0+0+0+8+4+0+1=13
        assertEquals("00111101", getBinaryString(a | b)); // 0+0+32+16+8+4+0+1=61
    }

    @Test
    public void xor() {
        int a = 60;
        int b = 13;
        assertEquals(49, a ^ b);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        assertEquals("00001101", getBinaryString(b)); // 0+0+0+0+8+4+0+1=13
        assertEquals("00110001", getBinaryString(a ^ b)); // 0+0+32+16+0+0+0+1=49
    }

    @Test
    public void leftShift() {
        int a = 60;
        int b = 2;
        assertEquals(240, a << b);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        assertEquals("11110000", getBinaryString(a << b));// 128+64+32+16+0+0+0+0=240
    }

    @Test
    public void rightShift() {
        int a = 60;
        int b = 2;
        assertEquals(15, a >> b);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        assertEquals("00001111", getBinaryString(a >> b));// 0+0+0+0+8+4+2+1=15
    }

    @Test
    public void unsignedRightShift() {
        int a = 60;
        int b = 2;
        assertEquals(15, a >>> b);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        assertEquals("00001111", getBinaryString(a >>> b));// 0+0+0+0+8+4+2+1=15
    }

    @Test
    public void rightShiftVsUnsignedRightShift() {
        // 00111100 >> 00001111
        assertEquals(15, 60 >> 2);

        // 11111111111111111111111111000100 >> 11111111111111111111111111110001
        // 0001 turns to 1110
        // add 1 turns to 1111 = 8+4+2+1 = -15
        assertEquals(-15, -60 >> 2);

        // 00111100 >> 00001111
        assertEquals(15, 60 >>> 2);

        // 11111111111111111111111111000100 >> 00111111111111111111111111110001
        assertEquals(1073741809, -60 >>> 2); // always positive
    }

    @Test
    public void compliment() {
        int a = 60;
        assertEquals(-61, ~a);
        assertEquals("00111100", getBinaryString(a)); // 0+0+32+16+8+4+0+0=60
        // they are flipping all 32 bits for int the getBinaryString returns 8
        // for simplicity
        assertEquals("111111111111111111111111" + "11000011",
                getBinaryString(~a));
        // Sign Bit (first) is 1 so the decimal will be negative.
        // Then we calculate Two's compliment
        // 1. flip bits = 00111100
        // 2. add 1 = 00111101 = 0+0+32+16+8+4+0+1=-61
    }

    @Test
    public void addingBinary() {
        int a = 11; // 1011 = 8+0+2+1
        int b = 1; // 0001
        // So you line them up in binary and start adding bits one-by-one.
        // When you have 1+1 it is going to be 2 in decimal but 10 in binary. So
        // you write 0 in the resulting bit and carry over 1 to the next slot.
        // result is 1100
        assertEquals(12, a + b);
    }
}