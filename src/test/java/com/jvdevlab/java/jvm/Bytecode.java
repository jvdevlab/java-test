package com.jvdevlab.java.jvm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bytecode {

    @Test
    public void disassembleTheCode() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("javap", "-c", "java.lang.Object");
        Process process = builder.start();

        String result = new String(process.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8);
        log.debug(result);
        assertTrue(result.contains("invokevirtual"));

        int exitCode = process.waitFor();
        assertEquals(0, exitCode);
    }

}
