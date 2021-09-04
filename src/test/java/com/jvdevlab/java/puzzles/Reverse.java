package com.jvdevlab.java.puzzles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Reverse {

    @Test
    public void testReverse() {
        testReverse("hello", "olleh", this::reverseWithExtraVariables);
        testReverse("hell", "lleh", this::reverseWithExtraVariables);

        testReverse("hello", "olleh", this::reverseWithoutExtraVariables);
        testReverse("hell", "lleh", this::reverseWithoutExtraVariables);

        testReverse("hello", "olleh", this::reverseWithoutExtraVariablesXOR);
        testReverse("hell", "lleh", this::reverseWithoutExtraVariablesXOR);
    }

    public char[] reverseWithExtraVariables(char[] chars) {
        for (int i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - 1 - i] = temp;
        }

        return chars;
    }

    public char[] reverseWithoutExtraVariables(char[] chars) {
        for (int i = 0; i < chars.length / 2; i++) {
            chars[i] += chars[chars.length - i - 1]; // 70 + 2 = 72
            chars[chars.length - 1 - i] = (char) (chars[i] - chars[chars.length - 1 - i]); // 72 - 2 = 70
            chars[i] = (char) (chars[i] - chars[chars.length - 1 - i]); // 72 - 70 = 2
        }

        return chars;
    }

    /**
     * A more elegant solution from the Internet. Fundamentally it uses the same
     * principal as the one I came up in reverseWithoutExtraVariables after some
     * pondering instead of using temp var we modify existing variable and then
     * derive the values that needs to be swapped from it.
     *
     * Even though the below solution is perhaps more performant and syntactically
     * short I would say it might be harder to comprehend if you are not familiar
     * with bitwise XOR. I would rank these as follows in terms of how easy is to
     * comprehend them:
     *
     * 1. reverseWithExtraVariables
     *
     * 2. reverseWithoutExtraVariables
     *
     * 3. reverseWithoutExtraVariablesXOR
     *
     * First understand XOR https://en.wikipedia.org/wiki/Exclusive_or "It gains the
     * name "exclusive or" because the meaning of "or" is ambiguous when both
     * operands are true; the exclusive or operator excludes that case. This is
     * sometimes thought of as "one or the other but not both". This could be
     * written as "A or B, but not, A and B"." Second understand bitwise XOR and how
     * it can be used to swap variables https://www.youtube.com/watch?v=mJG1DManmLc
     *
     * Also note, a different way of looping over the array - less verbose.
     */

    public char[] reverseWithoutExtraVariablesXOR(char[] chars) {
        // These are counters and not counted as extra vars.
        int i = 0, j = chars.length - 1;
        // Swap A and B.
        while (i < j) {
            // A: 01010101 XOR
            // B: 11111111 =
            // C: 10101010
            chars[i] ^= chars[j];
            // B: 11111111 XOR
            // C: 10101010 =
            // A: 01010101 (derived A)
            chars[j] ^= chars[i];
            // C: 10101010 XOR
            // A: 01010101
            // B: 11111111 (derived B)
            chars[i] ^= chars[j];
            i++;
            j--;
        }

        return chars;
    }

    interface IReverse {
        char[] reverse(char[] value);
    }

    public void testReverse(String input, String output, IReverse func) {
        char[] charsInput = input.toCharArray();
        char[] charsOutput = func.reverse(charsInput);
        assertEquals(charsInput, charsOutput);
        assertEquals(output, String.valueOf(charsOutput));
    }

}
