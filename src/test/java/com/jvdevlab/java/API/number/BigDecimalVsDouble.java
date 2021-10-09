package com.jvdevlab.java.API.number;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class BigDecimalVsDouble {

    @Test
    public void controlPrecision() {
        double d = 1.1 - 1;
        assertEquals(0.10000000000000009, d);

        BigDecimal bd = BigDecimal.valueOf(1.1).subtract(BigDecimal.valueOf(1));
        assertEquals(0.1, bd.doubleValue());
    }

}
