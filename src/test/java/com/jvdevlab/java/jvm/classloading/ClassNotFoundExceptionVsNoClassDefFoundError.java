package com.jvdevlab.java.jvm.classloading;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// https://www.baeldung.com/java-classnotfoundexception-and-noclassdeffounderror
public class ClassNotFoundExceptionVsNoClassDefFoundError {

    // "Java runtime throws ClassNotFoundException while trying to load a class at
    // runtime only and the name was provided during runtime."
    @Test
    public void classNotFoundException() {
        assertThrows(ClassNotFoundException.class, () -> Class.forName("oracle.jdbc.driver.OracleDriver"));
    }

    static class ClassWithInitErrors {
        static int data = 1 / 0;
    }

    // "In the case of NoClassDefFoundError, the class was present at compile time,
    // but Java runtime could not find it in Java classpath during runtime. It
    // usually happens when there is an exception while executing a static block or
    // initializing static fields of the class, so class initialization fails."
    @Test
    public void noClassDefFoundError() {
        // "ClassWithInitErrors initialization throws an exception. So, when we try to
        // create an object of ClassWithInitErrors, it throws
        // ExceptionInInitializerError. If we try to load the same class again, we get
        // the NoClassDefFoundError:"
        assertThrows(ExceptionInInitializerError.class, () -> new ClassWithInitErrors());
        assertThrows(NoClassDefFoundError.class, () -> new ClassWithInitErrors());
    }
}
