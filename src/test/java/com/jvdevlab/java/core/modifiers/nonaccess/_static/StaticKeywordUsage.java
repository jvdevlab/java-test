package com.jvdevlab.java.core.modifiers.nonaccess._static;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

// Illegal modifier for the class TopLevelStaticClass; only public, abstract & final are
// permitted.
// static class TopLevelStaticClass {}

@Slf4j
public class StaticKeywordUsage {

    static class StaticNestedClass {
    }

    class InnerClass {
    }

    // Mostly useful for constants. (in combination with final)
    static int classVariable = 1;

    int instanceVariable;

    // Static block is useful when you need multiple lines of code
    // to initialize a static variable or when you need a try catch block.
    static {
        log.debug("Before static block: classVariable = " + classVariable);
        try {
            classVariable = 2 / 0;
        } catch (Exception e) {
            // java.lang.ArithmeticException: / by zero
        } finally {
            classVariable = 2;
            log.debug("1st static block: classVariable = " + classVariable);
        }

        // Cannot make a static reference to the non-static field
        // instanceVariable = 2;
    }

    // Can have multiple static blocks.
    // static vars and static blocks are executed in order of declaration in the
    // class.
    static {
        classVariable = 3;
        log.debug("2nd static block classVariable = " + classVariable);
    }

    // Mostly useful for utility methods. E.g. Math.abs(a);
    static void classMethod() {
        classVariable = 4;

        // Cannot make a static reference to the non-static field
        // instanceVariable
        // instanceVariable = 4;

        // Cannot use this in a static context
        // this.instanceVariable = 4;

        // Cannot use super in a static context
        // super.clone();

        new StaticKeywordUsage().instanceVariable = 4;
    }

    void instanceMethod() {
        classVariable = 5;

        instanceVariable = 5;

        // Warning: The static field StaticKeywordUsage.classVariable should be
        // accessed
        // in a static way.
        // this.classVariable = 5;

        this.instanceVariable = 5;
    }

    @Test
    public void staticUsage() {

        // Illegal modifier for parameter localVariable; only final is permitted
        // static int localVariable;

        // Illegal modifier for the local class MethodLocalInnerClass; only
        // abstract or
        // final is permitted.
        // static class MethodLocalInnerClass {}

        assertEquals(3, StaticKeywordUsage.classVariable);
        // Warning: The static field StaticKeywordUsage.classVariable should be
        // accessed
        // in a static way.
        // assertEquals(3, new StaticKeywordUsage().classVariable);

        StaticKeywordUsage.classMethod();
        assertEquals(4, StaticKeywordUsage.classVariable);

        new StaticKeywordUsage().instanceMethod();
        assertEquals(5, StaticKeywordUsage.classVariable);

        @SuppressWarnings("unused")
        final StaticKeywordUsage.StaticNestedClass staticNestedClass = new StaticKeywordUsage.StaticNestedClass();
        @SuppressWarnings("unused")
        final StaticKeywordUsage.StaticNestedClass staticNestedClass2 = new StaticNestedClass();

        // Illegal enclosing instance specification for type
        // StaticKeywordUsage.StaticNestedClass.
        // StaticKeywordUsage.StaticNestedClass staticNestedClass2 = new
        // StaticKeywordUsage().new StaticNestedClass();

        @SuppressWarnings("unused")
        final StaticKeywordUsage.InnerClass innerClass = new StaticKeywordUsage().new InnerClass();
    }

}
