package com.jvdevlab.java.oop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AbstractClassVsInterface {

    @Test
    @SuppressWarnings("unused")
    public void abstractClassVsInterface() {
        abstract class A {
            private int i = 0;

            private int getI() {
                return i;
            }

            protected int j = 0;

            protected int getJ() {
                return j;
            }

            static int k = 0;

            static int getK() {
                return k;
            }
        }

        interface B {
            // Can't have private properties
            // private int i = 0;
            // But can have private methods
            private int getI() {
                return 1;
            }

            default int getIDefault() {
                return getI();
            }

            // Can't have protected properties or methods
            // protected int j = 0;
            // protected int getJ(){
            // return 0;
            // }

            static int k = 0;

            static int getK() {
                return 0;
            }
        }

        B b = new B() {
        };

        assertEquals(1, b.getIDefault());
    }
}
