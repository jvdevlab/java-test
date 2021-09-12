package com.jvdevlab.java.core.modifiers.nonaccess;

public class NonAccessModifiers {

    // final doesn't go together with abstract
    public static abstract strictfp class MyClass {

        // final doesn't go together with volatile
        static transient volatile float property = 0f;

        // abstract doesn't go together with static or synchronized
        final static synchronized native void method();
    }

}
