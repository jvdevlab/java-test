package com.jvdevlab.java.jvm.diagnostics.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuntimeAPI {

    @Test
    public void memoryInfo() {
        log.debug(System.lineSeparator() + "Memory Information:");
        Runtime runtime = Runtime.getRuntime();
        long freeMem = runtime.freeMemory();
        log.debug("The amount of free memory in the JVM: " + freeMem);
        long maxMem = runtime.maxMemory();
        log.debug("The maximum amount of memory that the JVM will attempt to use: " + maxMem);
        long totalMem = runtime.totalMemory();
        log.debug("The total amount of memory in the JVM: " + totalMem);

        assertTrue(freeMem < maxMem);
        assertTrue(freeMem < totalMem);
    }

    @Test
    public void availableProcessors() {
        Runtime runtime = Runtime.getRuntime();
        int availableProcessors = runtime.availableProcessors();
        log.debug(System.lineSeparator() + "Available Processors: " + availableProcessors);
        assertTrue(availableProcessors > 0);
    }

}
