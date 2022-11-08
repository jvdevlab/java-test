package com.jvdevlab.java.core.modifiers.others;

public class NonAccessModifiers {

    // final doesn't go together with abstract
    // Note: "Floating-point expressions are always strictly evaluated from
    // source level 17. Keyword 'strictfp' is not required."
    public static abstract class MyClass {

        // final doesn't go together with volatile
        static transient volatile float property = 0f;

        // abstract doesn't go together with static or synchronized
        final static synchronized native void method();
    }

}
