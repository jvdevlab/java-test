package com.jvdevlab.java.core.modifiers.access.package1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccessFromSamePackage {

    @Test
    public void accessPublicClassFields() {
        PublicAccessClass pubClass = new PublicAccessClass();
        assertEquals(0, pubClass.publicAccess);
        assertEquals(0, pubClass.defaultAccess);
        assertEquals(0, pubClass.protectedAccess);
        // Compile-time error "The field PublicClass.privateAccess is not visible"
        // assertEquals(0, pubClass.privateAccess);
    }

    @Test
    public void accessDefaultClassFields() {
        DefaultAccessClass defClass = new DefaultAccessClass();
        assertEquals(0, defClass.publicAccess);
        assertEquals(0, defClass.defaultAccess);
        assertEquals(0, defClass.protectedAccess);
        // Compile-time error "The field PublicClass.privateAccess is not visible"
        // assertEquals(0, defClass.privateAccess);
    }

}
