package com.jvdevlab.java.core.modifiers.nonaccess._static;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StaticTest {

    @Test
    @SuppressWarnings("static-access")
    public void shouldHideStaticMethod() {
        assertEquals("parent", Parent.name());
        assertEquals("parent", new Parent().name());

        assertEquals("child", Child.name());
        // Can not override but can hide. Don't do that.
        assertEquals("child", new Child().name());
    }

}

class Parent {
    static String name() {
        return "parent";
    }
}

class Child extends Parent {
    static String name() {
        return "child";
    }
}
