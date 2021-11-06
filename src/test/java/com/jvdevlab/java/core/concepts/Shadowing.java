package com.jvdevlab.java.core.concepts;

import org.junit.jupiter.api.Test;

public class Shadowing {

    int i;

    @SuppressWarnings("unused")
    @Test
    public void shadowing() {
        int i;

    }
}
