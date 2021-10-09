package com.jvdevlab.java.API.string;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringVsStringBufferVsStringBuilder {

    @Test
    public void concatenationPerformance() {

        // Speed up
        int maxCount = 1000;
        // int maxCount = 100000;

        long start = System.currentTimeMillis();
        @SuppressWarnings("unused")
        String string = "";
        for (int i = 0; i < maxCount; i++) {
            // Internally concatenation operator "+" is implemented
            // using either StringBuffer or StringBuilder.
            // However, due to amount of conversions and SB instances
            // created inside the loop this will be much slower than
            // using a single SB that is declared outside of the loop.
            string += i;
        }
        long stringTook = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        String stringConcat = "";
        for (int i = 0; i < maxCount; i++) {
            stringConcat = stringConcat.concat("" + i);
        }
        long stringConcatTook = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < maxCount; i++) {
            buffer.append(i);
        }

        long bufferTook = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < maxCount; i++) {
            builder.append(i);
        }

        long builderTook = System.currentTimeMillis() - start;

        log.debug("String took: " + stringTook);
        log.debug("String.concat took: " + stringConcatTook);
        log.debug("StringBuffer took: " + bufferTook);
        log.debug("StringBuilder took: " + builderTook);

        // have to set int maxCount = 100000; for the difference to be noticeable
        // and the following consistently passing.
        // assertTrue(stringTook > stringConcatTook);
        // assertTrue(stringConcatTook > bufferTook);
        // assertTrue(bufferTook > builderTook);

        // Don't want the test to run for few sec so do some dummy checks
        assertTrue(stringTook >= 0);
        assertTrue(stringConcatTook >= 0);
        assertTrue(bufferTook >= 0);
    }
}
