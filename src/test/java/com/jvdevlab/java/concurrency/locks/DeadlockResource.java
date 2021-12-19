package com.jvdevlab.java.concurrency.locks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Thread.State;

import org.junit.jupiter.api.Test;

public class DeadlockResource {

    private String monitor1 = new String();
    private String monitor2 = new String();

    @Test
    public void deadlock() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (monitor1) {
                // do some work that takes at least enough time for t2 to
                // acquire monitor2
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }

                // note also how next block of code is nested so the monitor1 is
                // not released yet.
                synchronized (monitor2) {
                    // do some other work
                }
            }

        });

        // Note how t2 acquires monitors in revers order - and that is the
        // problem!
        Thread t2 = new Thread(() -> {
            synchronized (monitor2) {
                // do some work that takes at least enough time for t1 to
                // acquire monitor1
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }

                synchronized (monitor1) {
                    // do some other work
                }
            }
        });

        t1.start();
        t2.start();
        Thread.sleep(200);

        assertEquals(State.BLOCKED, t1.getState());
        assertEquals(State.BLOCKED, t2.getState());
    }
}
