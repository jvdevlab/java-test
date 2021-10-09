package com.jvdevlab.java.jvm.diagnostics.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class BuiltInTools {

    // Note from https://adoptopenjdk.net/ you need to install latest, Hotspot,
    // windows, x64, JDK. Not JRE otherwise the jps and jinfo will be missing.
    @Test
    public void processInfo() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("jps");
        Process process = builder.start();

        StringBuilder result = new StringBuilder();
        result.append(new String(process.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8));

        String pids[] = result.toString().split("\n");
        assertNotNull(pids);

        int exitCode = process.waitFor();
        assertEquals(0, exitCode);

        // have to loop over all pids as some of them might could exit by this
        // time
        for (String pid : pids) {
            pid = pid.split(" ")[0];
            builder.command("jinfo", pid);
            process = builder.start();
            result.append(new String(process.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8));
            process.waitFor(); // some might return 1
        }
        assertTrue(result.toString().contains("Java System Properties"));

        // log.debug(result.toString());
    }

}
