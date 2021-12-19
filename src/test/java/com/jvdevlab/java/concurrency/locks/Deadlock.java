package com.jvdevlab.java.concurrency.locks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Thread.State;

import org.junit.jupiter.api.Test;

public class Deadlock {

    class A {
        synchronized void methodA1(B b) {
            // give other thread a change to acquire its lock
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }

            b.methodB2();
        }

        synchronized void methodA2() {
        }
    }

    class B {
        synchronized void methodB1(A a) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }

            a.methodA2();
        }

        synchronized void methodB2() {
        }
    }

    @Test
    public void deadlock() throws InterruptedException {
        A a = new A();
        B b = new B();

        Thread t1 = new Thread(() -> a.methodA1(b));
        Thread t2 = new Thread(() -> b.methodB1(a));

        t1.start();
        t2.start();
        Thread.sleep(200);

        assertEquals(State.BLOCKED, t1.getState());
        assertEquals(State.BLOCKED, t2.getState());
    }
}
