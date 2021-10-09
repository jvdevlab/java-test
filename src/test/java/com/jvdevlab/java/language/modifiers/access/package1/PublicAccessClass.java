package com.jvdevlab.java.language.modifiers.access.package1;

// A java file can have only one public top level class
public class PublicAccessClass {
    public int publicAccess;
    int defaultAccess;
    protected int protectedAccess;
    @SuppressWarnings("unused")
    private int privateAccess;

    // The above modifiers work the same way for methods and inner classes.
}

// Can't make a class either protected or private, only public or "default"
class DefaultAccessClass {
    public int publicAccess;
    int defaultAccess;
    protected int protectedAccess;
    @SuppressWarnings("unused")
    private int privateAccess;
}
