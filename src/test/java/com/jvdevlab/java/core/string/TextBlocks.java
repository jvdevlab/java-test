package com.jvdevlab.java.core.string;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// Second Preview since Java 14
// JEP 368: Text Blocks (Second Preview) https://openjdk.java.net/jeps/368
public class TextBlocks {

        @Test
        public void twoDimensionalBlockOfText() {
                String html = """
                                <html>
                                    <body>
                                        <p>Hello World!</p>
                                    </body>
                                </html>
                                """;
                assertEquals(5, html.chars().filter(c -> c == '\n').count());
        }

        @Test
        public void charsEscaping() {
                String string = """
                                No need to escape: "hello"
                                This is not necessary: \"
                                This is not necessary: \n
                                Must escape this \"""hi\"""
                                """;
                assertTrue(string.contains("\"hello\""));
                assertTrue(string.contains("\"\"\"hi\"\"\""));

                // There are 2 new escape sequences to allow finer control:
                string = """
                                1. Suppresses the insertion of a newline character \
                                line continues.
                                """;
                assertTrue(string.contains("character line continues."));

                string = """
                                \s 2. Keep the surrounding whitespace \s
                                """;
                assertTrue(string.contains(" 2."));
                assertTrue(string.contains("whitespace "));
        }

        @Test
        // @see StringPool
        public void interning() {
                String s = "Hello!";
                String s2 = """
                                Hello!""";
                // "At run time, a text block is evaluated to an instance of String, just like a
                // string literal. Instances of String that are derived from text blocks are
                // indistinguishable from instances derived from string literals. Two text
                // blocks with the same processed content will refer to the same instance of
                // String due to interning, just like for string literals."
                assertTrue(s == s2);
        }

        @Test
        public void concatenation() {
                String result = "String literal " + """
                                String block""" + " Another literal";
                assertTrue(result.contains("block"));

                int arg = 0;
                String clunky = """
                                function(""" + arg + """
                                )
                                """;
                assertTrue(clunky.contains("0"));

                String better = String.format("""
                                function(%d)""", arg);
                assertTrue(better.contains("0"));

                // using new 'formatted' method
                String evenBetter = """
                                function(%d)""".formatted(arg);
                assertTrue(evenBetter.contains("0"));
        }
}
