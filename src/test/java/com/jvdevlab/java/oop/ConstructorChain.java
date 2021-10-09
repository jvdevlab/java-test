package com.jvdevlab.java.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConstructorChain {

    @Test
    public void constructorChain() {
        class A {
            int i = 0;

            A() {
                this.i = 1;
            }
        }

        class B extends A {
            // implicit default constructor like so
            // B(){ super(); }
        }

        class C extends A {
            C() {
                log.debug(C.class.getName());
                // "Constructor call must be the first statement in a
                // constructor"
                // super();
            }

        }

        assertEquals(1, new B().i);
        assertEquals(1, new C().i);
    }

}
