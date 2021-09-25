package com.jvdevlab.java.core.numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NumberSystem {

    @Test
    public void numberSystems() {
        int dec = 2748;
        int bin = 0b101010111100;
        int oct = 05274;
        int hex = 0xABC;

        assertEquals(dec, bin);
        assertEquals(dec, oct);
        assertEquals(dec, hex);
    }

}
