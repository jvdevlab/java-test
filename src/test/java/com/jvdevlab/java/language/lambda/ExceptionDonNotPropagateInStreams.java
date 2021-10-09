package com.jvdevlab.java.language.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ExceptionDonNotPropagateInStreams {

    @Test
    public void exceptionDonNotPropagateInStreams() {
        // "One key feature of exceptions is that they can be thrown at a low level,
        // bubbled up and dealt with at a high level---"throw early, catch late". The
        // issue with checked exceptions is that every intermediate level needs to
        // declare the exception to be thrown. This is particularly problematic when the
        // intermediate levels include interfaces that doesn't allow checked exceptions
        // to be thrown."

        // "This problem arises all the time with callbacks, as the API accepting a
        // callback doesn't know what exceptions the client might want to throw. To give
        // an example, here's a snippet that uses the Stream API:"

        // Compiler Error: Unhandled exception type IOException
        // long sum = new ArrayList<Path>().stream().mapToLong(e ->
        // Files.size(e)).sum();

        // Compiler Error: Unhandled exception type IOException
        // Compiler error: Unreachable catch block for IOException. This exception is
        // never thrown from the try statement body
        // try {
        // long sum = new ArrayList<Path>().stream().mapToLong(e ->
        // Files.size(e)).sum();
        // } catch (IOException e) {
        // // do nothing
        // }

        // "This doesn't compile since Files.size throws IOException which is not
        // allowed to propagate through mapToLong function."

        // This has a "workaround" for the above specific issue. But the problem exists
        // not just for steaming API
        // https://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception

        @SuppressWarnings("unused")
        long sum = new ArrayList<Path>().stream().mapToLong(e -> {
            try {
                return Files.size(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }).sum();

        // Or using external library if you don't want to roll your own solution
        // Errors class from https://github.com/diffplug/durian

        // "Imagine if checked exceptions worked as poorly with "for" as it does with
        // forEach!"
    }
}
