package com.jvdevlab.java.language.statements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Switch {

    @Test
    public void before() {
        String result = "";
        int value = 1;
        switch (value) {
            case 1:
                result = "A";
                // break; accidental fall-through
            case 2:
                result = "B";
                break;
            default:
                result = "C";
        }
        assertEquals("B", result);
    }

    @Test
    public void sinceJava14NewSyntaxAndNoAccidentalFallTrhough() {
        String result = "";
        int value = 1;
        switch (value) {
            case 1 -> result = "A";
            case 2 -> result = "B";
            default -> result = "C";
        }
        assertEquals("A", result);
    }

    @Test
    public void orEventBetterUsingExpression() {
        int value = 1;
        String result = switch (value) {
            case 1 -> "A";
            case 2 -> "B";
            default -> "C";
        };
        assertEquals("A", result);
    }

    @Test
    public void canAlsoUseYieldEspeciallyInABlock() {
        int value = 1;
        String result = switch (value) {
            case 1: {
                // some more complex logic
                String a = new String("A");
                a = a.intern();
                yield a;
            }
            case 2:
                yield "B";
            default:
                yield "C";

        };
        assertEquals("A", result);
    }

}
