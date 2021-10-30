package com.jvdevlab.java.core.modifiers.access;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.jvdevlab.java.core.modifiers.access.package1.PublicAccessClass;

// Compile-time error:
// The type com.vj.java.core.modifiers.access.package1.DefaultAccessClass is not visible
//import com.vj.java.core.modifiers.access.package1.DefaultAccessClass;

public class AccessFromDifferentPackage {

    @Test
    public void accessPublicClassFields() {
        PublicAccessClass pubClass = new PublicAccessClass();
        assertEquals(0, pubClass.publicAccess);
        // Compile-time error "The field PublicClass._ is not visible"
        // assertEquals(0, pubClass.defaultAccess);
        // assertEquals(0, pubClass.protectedAccess);
        // assertEquals(0, pubClass.privateAccess);
    }

    @Test
    public void accessPublicClassProtectedField() {

        class ExtendedPubClass extends PublicAccessClass {
            public int callProtectedAccess() {
                return super.protectedAccess;
            }
        }

        ExtendedPubClass extendedPubClass = new ExtendedPubClass();
        assertEquals(0, extendedPubClass.publicAccess);
        assertEquals(0, extendedPubClass.callProtectedAccess());

        // In a different package you can only call protected from
        // WITHIN the extended class.
        // assertEquals(0, extendedPubClass.protectedAccess);

        // Compile-time error "The field PublicClass._ is not visible"
        // assertEquals(0, extendedPubClass.defaultAccess);
        // assertEquals(0, extendedPubClass.privateAccess);
    }

}
