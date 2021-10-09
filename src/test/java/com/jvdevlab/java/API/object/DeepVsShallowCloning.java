package com.jvdevlab.java.API.object;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeepVsShallowCloning {

    @Test
    public void shallowCloning() throws CloneNotSupportedException {
        class Child {
        }

        class Parent implements Cloneable {
            Child child = new Child();

            @Override
            public Object clone() throws CloneNotSupportedException {
                Parent clone = (Parent) super.clone();
                return clone;
            }
        }

        Parent parent = new Parent();
        Parent clone = (Parent) parent.clone();

        assertTrue(parent != clone);
        assertTrue(parent.child == clone.child);
    }

    @Test
    public void deepCloning() throws CloneNotSupportedException {
        class Child implements Cloneable {
            @Override
            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }

        class Parent implements Cloneable {
            Child child = new Child();

            @Override
            public Object clone() throws CloneNotSupportedException {
                Parent clone = (Parent) super.clone();
                clone.child = (Child) child.clone();
                return clone;
            }
        }

        Parent parent = new Parent();
        Parent clone = (Parent) parent.clone();

        assertTrue(parent != clone);
        assertTrue(parent.child != clone.child);
    }

    // https://www.artima.com/articles/josh-bloch-on-design#part13
    @Test
    public void useCopyConstructorInsteadOfCloning() {
        class Child {
            // can also have a copy constructor
        }

        class Parent {
            Child child = new Child();

            Parent() {
            }

            Parent(Parent copy) {
                this.child = new Child();
            }
        }

        Parent parent = new Parent();
        Parent clone = new Parent(parent);

        assertTrue(parent != clone);
        assertTrue(parent.child != clone.child);

    }
}
