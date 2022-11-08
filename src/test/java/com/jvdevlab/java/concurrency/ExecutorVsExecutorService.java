package com.jvdevlab.java.concurrency;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

public class ExecutorVsExecutorService {

    @Test
    public void directExecutor() {
        Executor executorService = new Executor() {
            @Override
            public void execute(Runnable task) {
                task.run();
            }
        };

        StringBuffer name = new StringBuffer();
        executorService
                .execute(() -> name.append(Thread.currentThread().getName()));
        assertTrue(name.toString().contains("main"));
    }

    @Test
    public void threadPerTaskExecutor() throws InterruptedException {
        Executor executorService = new Executor() {
            @Override
            public void execute(Runnable task) {
                Thread custom = new Thread(task);
                custom.setName("custom");
                custom.start();
            }
        };

        StringBuffer name = new StringBuffer();
        executorService
                .execute(() -> name.append(Thread.currentThread().getName()));

        Thread.sleep(200);
        assertTrue(name.toString().contains("custom"));
    }

    @Test
    public void singleThreadExecutorService() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> result = executorService
                .submit(() -> Thread.currentThread().getName());

        // blocking call.
        String name = result.get();

        // name = pool-X-thread-Y
        assertTrue(name.contains("pool-"));
        assertTrue(name.contains("-thread-"));
    }
}
