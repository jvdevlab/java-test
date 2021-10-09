package com.jvdevlab.java.API.collections.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

import org.junit.jupiter.api.Test;

public class DequeVsStack {

    @Test
    public void dequeVsStack() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        assertEquals("c", stack.pop());
        assertEquals(2, stack.size());

        // "Deque interface should be used in
        // preference to the legacy Stack class"
        Deque<String> betterStack = new ArrayDeque<>();
        betterStack.push("a");
        betterStack.push("b");
        betterStack.push("c");
        assertEquals("c", betterStack.pop());
        assertEquals(2, betterStack.size());
    }

}
