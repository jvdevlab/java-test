package com.jvdevlab.java.concurrency;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandlingInterruptedException {

    @Test
    public void interrupt() throws InterruptedException {
        Thread batchProcess = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                log.debug("Gracefully recover");
                // Once the exception is thrown, the thread is no longer in an
                // interrupted state.
                assertFalse(Thread.currentThread().isInterrupted());
            }
        });
        batchProcess.start();
        Thread.sleep(500);

        Thread interrupter = new Thread(() -> batchProcess.interrupt());
        interrupter.start();
        Thread.sleep(500);
    }

}
