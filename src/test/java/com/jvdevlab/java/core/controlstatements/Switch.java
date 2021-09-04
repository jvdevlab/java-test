package com.jvdevlab.java.core.controlstatements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Switch {

    @Test
    public void traditionalSwitchWithAccidentalFallTrhoguh() {
        String result = "";
        int value = 1;
        switch (value) {
            case 1:
                result = "A";
                // break;
            case 2:
            case 3:
            case 4:
                result = "B";
                break;
            default:
                result = "C";
        }
        assertEquals("B", result);
    }

    // Standard since Java 14
    @Test
    public void arrowLabelSwitchWithNoFallThrough() {
        String result = "";
        int value = 1;
        switch (value) {
            case 1 -> result = "A";
            case 2, 3, 4 -> result = "B";
            default -> result = "C";
        }
        assertEquals("A", result);
    }

    @Test
    public void traditionalSwitchWithLocalVariableScope() {
        int value = 1;
        switch (value) {
            case 1:
                int number = 0;
                // break;
            case 2:
            case 3:
            case 4:
                number = 1;
                // break;
            default:
                number = 3;
                assertEquals(3, number);
        }
    }

    // Standard since Java 14
    // "This eliminates another annoyance with traditional switch blocks where the
    // scope of a local variable is the entire block"
    @Test
    public void noFallThroughVariableScope() {
        int value = 1;
        switch (value) {
            case 1 -> {
                @SuppressWarnings("unused")
                int number = 0;
            }
            case 2, 3, 4 -> {
                // Compilation Error: "number cannot be resolved to a variable"
                // number = 1;
            }
            default -> {
                // Compilation Error: "number cannot be resolved to a variable"
                // number = 2;
            }
        }

    }

    // Standard since Java 14
    @Test
    public void swtichExpression() {
        int value = 1;
        String result = switch (value) {
            case 1 -> "A";
            case 2, 3, 4 -> "B";
            default -> "C";
        };
        assertEquals("A", result);
    }

    // Standard since Java 14
    @Test
    public void switchExpressionWithYield() {
        int value = 0;
        String result = switch (value) {
            case 1 -> "A";
            case 2, 3, 4 -> "B";
            default -> {
                String temp = "";
                String[] array = { "C", "D", "E" };
                if (value <= array.length) {
                    temp = array[value];
                }
                yield temp;
            }
        };
        assertEquals("C", result);
    }

    // Standard since Java 14
    @Test
    public void switchExpressionWithoutArrowLabels() {
        int value = 1;
        String result = switch (value) {
            case 1:
                yield "A";
            case 2, 3, 4:
                yield "B";
            default:
                yield "C";
        };
        assertEquals("A", result);

    }

    @Test
    public void switchExpressionMustBeExhaustive() {
        int value = 1;
        String result = switch (value) {
            case 1:
                // "Void methods cannot return a value"
                // return "A";
                // "break out of switch expression not allowed"
                // break;
            default:
                // "A switch expression should have at least one result expression"
                // "The cases of a switch expression must be exhaustive; for all possible values
                // there must be a matching switch label. (Obviously switch statements are not
                // required to be exhaustive.)"
                // "In practice this normally means that a default clause is required; however,
                // in the case of an enum switch expression that covers all known constants, a
                // default clause is inserted by the compiler to indicate that the enum
                // definition has changed between compile-time and runtime."
                yield "C";
        };
        assertEquals("C", result);
    }
}
