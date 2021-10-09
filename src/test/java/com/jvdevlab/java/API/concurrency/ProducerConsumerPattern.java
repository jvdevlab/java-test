package com.jvdevlab.java.API.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class ProducerConsumerPattern {

    @Test
    public void producerConsumerPattern() throws InterruptedException {

        class Producer implements Runnable {
            BlockingQueue<String> queue;

            Producer(BlockingQueue<String> queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                IntStream.rangeClosed(1, 10).forEach(i -> {
                    try {
                        queue.put("test " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });
            }
        }

        class Consumer implements Runnable {
            BlockingQueue<String> queue;

            Consumer(BlockingQueue<String> queue) {
                this.queue = queue;
            }

            @Override
            public void run() {
                IntStream.rangeClosed(1, 15).forEach(i -> {
                    try {
                        System.out.println(queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));

        assertEquals(5, queue.remainingCapacity());
        producer.start(); // produces 10 messages.
        Thread.sleep(500);
        assertEquals(0, queue.remainingCapacity());
        // still needs to add 5
        assertEquals(Thread.State.WAITING, producer.getState());

        // tries to consume 15 messages.
        // 5 on the queue + another 5 producer will add
        // + blocks waiting on another 5
        consumer.start();
        Thread.sleep(500);
        assertEquals(5, queue.remainingCapacity());
        // wait for another 5
        assertEquals(Thread.State.WAITING, consumer.getState());
        assertEquals(Thread.State.TERMINATED, producer.getState());
    }

}
