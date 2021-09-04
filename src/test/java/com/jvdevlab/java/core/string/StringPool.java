package com.jvdevlab.java.core.string;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringPool {

    /**
     * All literal strings and string-valued constant expressions are interned.
     * String literals are defined in section 3.10.5 of the The Java™ Language
     * Specification.
     */
    @Test
    public void stringPool() {
        // "a" is added to SCP and refA1 is returned
        String a1 = "a";
        // "a" is already in SCP so refA1 is returned.
        String a2 = "a";
        assertTrue(a1 == a2);
        // "a" is already in SCP
        // but a new refA2 is created on the heap and returned.
        String a3 = new String("a");
        assertTrue(a3 != a1);

        // "b" is added to SCP with refB1
        // but a new refB2 is created on the heap and returned.
        String b1 = new String("b");
        // "b" is already in SCP and we get refB1.
        String b2 = "b";
        assertTrue(b1 != b2);
    }

    /**
     * https://stackoverflow.com/questions/60694819/java-string-pool-intern-and-concat?noredirect=1#comment107388494_60694819
     * Nothing special happens during concat() with respect to interning - concat()
     * creates a new string in the same way that new String("a") does. The
     * difference between your two examples are these: String a1 = new String("a");
     * creates a copy of the interned string constant "a". String a2 = a1.intern();
     * looks up the string "a" in the constant pool and assigns a reference to the
     * interned string to a2. String a3 = "a"; assigns a reference to the same
     * interned string constant "a" to a3. In the second example String bc1 = new
     * String("b").concat("c"); creates a new string "bc" that is not in the string
     * pool. String bc2 = bc1.intern(); looks into the string pool, doesn't find
     * "bc" there and therefore puts the string that is referenced by b1 into the
     * string pool. String bc3 = "bc"; looks up the string "bc" in the string pool
     * and already finds it there (the string referenced by b1 and b2) and assigns a
     * reference the that string also to b3. Beware that the behavior of your second
     * example (bc) changed between Java 5 and Java 7. In Java 5 the string constant
     * "bc" was already put into the string pool during class loading - running your
     * code with Java 5 gives the result that bc1 != bc2!
     */
    @Test
    public void internAndConcat() {
        // "a" is added to SCP with refA1
        // but a new refA2 is created on the heap and returned.
        String a1 = new String("a");
        // We try to intern refA2 but "a" is already in the SCP
        // so refA1 is returned.
        /*
         * intern() - "Returns a canonical representation (If something has canonical
         * status, it is accepted as having all the qualities that a thing of its kind
         * should have.) for the string object. When the intern method is invoked, if
         * the pool already contains a string equal to this String object as determined
         * by the equals(Object) method, then the string from the pool is returned.
         * Otherwise, this String object is added to the pool and a reference to this
         * String object is returned."
         */
        String a2 = a1.intern();
        assertTrue(a1 != a2);
        // "a" is already in SCP and we get refA1.
        String a3 = "a";
        assertTrue(a2 == a3);

        // Now concatenation.
        // From JLS 3.10.5
        // "Strings computed by constant expressions (§15.28) are computed at compile
        // time and then treated as if they were literals."
        // So "ab" == "a" + "b"
        // "Strings computed by concatenation at run time are newly created and
        // therefore distinct."
        // So "a".concat("b") != "ab" AND
        // String ab = "a"
        // String ab += "b"
        // ab != "ab"

        // "b" is added to SCP with refB
        // "c" is added to SCP with refC
        // "bc" is created BUT not interned (not added to SCP)
        // as it wasn't declared in "" but created at runtime.
        // In Java 5 it would be added to SCP.
        // a new reference refBC is created on the heap and returned.
        String bc1 = "b".concat("c");
        // Effect is the same if we do
        // String bc1 = new String("b").concat("c");
        // or
        // String bc1 = new String("b").concat(new String("c"));
        // now "bc" is added to SCP and refBC is returned.
        String bc2 = bc1.intern();
        assertTrue(bc1 == bc2);
        // "bc" is already in SCP and we get refBC.
        String bc3 = "bc";
        assertTrue(bc2 == bc3);

        // This is similar to above but instead of
        // concat() we use +=
        // "d" is added to SCP with refD
        String de1 = "d";
        // "e" is added to SCP with refE
        // "de" is created BUT not interned (not added to SCP) ???
        // a new reference refDE is created on the heap and returned.
        de1 += "e";
        // "de" is added to SCP ??? with A NEW refDE2.
        String de2 = de1.intern();
        // SO this is different compared to concat() above.
        assertTrue(de1 != de2);
        // "de" is already in SCP and we get refDE2.
        String de3 = "de";
        assertTrue(de2 == de3);

        // NOW this, even though similar to above has a
        // different behavior.
        // "fg" is added to SCP with refFG
        String fg1 = "fg";
        // This is fundamentally different from above concat()
        // and += as this concatenation is happening at compile
        // time and not run time. So it will be the same refFG
        String fg2 = "f" + "g";
        assertTrue(fg1 == fg2);

        // If we reverse the steps for concat() or +=
        // we will have different behavior.
        // "fg" is added to SCP with refHI1
        String hi1 = "hi";
        // "h" is added to SCP with refH
        // "i" is added to SCP with refI
        // "hi" is already in SCP but a new refHI2 is created and returned.
        String hi2 = "h".concat("i");
        assertTrue(hi1 != hi2);
    }

}
