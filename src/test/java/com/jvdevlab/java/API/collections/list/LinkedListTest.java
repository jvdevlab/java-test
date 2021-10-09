package com.jvdevlab.java.API.collections.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Test;

public class LinkedListTest {

    @Test
    public void linkedList() {

        // LinkedList is a beast. It implements both List and Deque
        // (pronounced "deck") which in turn extends Queue
        List<String> list = new LinkedList<>();
        // Faster structural modifications compared to ArrayList
        assertTrue(list instanceof List);
        // Can be used as a Deque, Stack (LIFO) or Queue (FIFO)
        assertTrue(list instanceof Deque);
        assertTrue(list instanceof Queue);

        Deque<String> deck = new LinkedList<>();
        // Support elements insertion/removal at both ends.
        deck.addFirst("c");
        deck.addFirst("b");
        deck.addFirst("a");

        deck.addLast("x");
        deck.addLast("y");
        deck.addLast("z");

        // peeking (get will do the same)
        assertEquals("a", deck.peekFirst());
        assertEquals("z", deck.peekLast());

        // retrieve with removal
        assertEquals("a", deck.removeFirst());
        assertEquals("b", deck.removeFirst());
        assertEquals("z", deck.removeLast());
        assertEquals("y", deck.removeLast());

        assertEquals(2, deck.size()); // c, x

        // Note, how method names are more intuitive then push and pop.
    }

    @Test
    public void fifoQueue() {

        Queue<String> queue = new LinkedList<>(); // Could use Deque too.

        // FIFO Queue. Deque supports it using addLast and removeFirst,
        // but you can go with add, pop and peek as Deque implements Queue.
        queue.add("a");
        queue.add("b");
        queue.add("c"); // c > b > a. deck.addLast
        assertEquals("a", queue.remove()); // c > b. removeFirst
    }

    @Test
    public void lifoStack() {
        Deque<String> stack = new LinkedList<>(); // Preferred to Stack

        // LIFO Stack. Deque supports it using addFirst and removeFirst,
        // but you can use push, pop and peek. This is preferred to old Stack.
        stack.push("c");
        stack.push("b");
        stack.push("a"); // c < b < a. deck.addLast
        assertEquals("a", stack.pop()); // c < b. deck.removeFirst
    }

}
