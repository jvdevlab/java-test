package com.jvdevlab.java.concurrency;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class DaemonThread {

    @Test
    public void daemonThread() {
        Thread daemon = new Thread(() -> System.out.println("daemon"));
        daemon.setDaemon(true);
        daemon.start();
    }

}
