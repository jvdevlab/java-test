package com.jvdevlab.java.core.API.string;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TextBlock {

    @Test
    public void textBlock() {
        String text = """
                <html>
                    <body>
                        <p>%s world!</p>
                    </body>
                </html>
                """.formatted("Hello");

        assertTrue(text.contains("Hello"));
    }

}
