package com.jvdevlab.java.concurrency;

import org.junit.jupiter.api.Test;

public class DaemonThread {

    @Test
    public void daemonThread() {
        Thread daemon = new Thread(() -> System.out.println("daemon"));
        daemon.setDaemon(true);
        daemon.start();
    }

}
