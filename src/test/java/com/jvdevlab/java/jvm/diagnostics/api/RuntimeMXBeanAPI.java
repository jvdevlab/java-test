package com.jvdevlab.java.jvm.diagnostics.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RuntimeMXBeanAPI {

    @Test
    public void inputArguments() {
        log.debug(System.lineSeparator() + "JVM Input Arguments:");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> inputArgs = runtimeMXBean.getInputArguments();
        inputArgs.forEach(log::info);
        assertNotNull(inputArgs);
    }

    @Test
    public void jvmUptime() {
        log.debug(System.lineSeparator() + "JVM Uptime:");
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long startTime = runtimeMXBean.getStartTime();
        log.debug("JVM start time: " + startTime);
        long uptimeTime = runtimeMXBean.getUptime();
        log.debug("JVM uptime: " + uptimeTime);
        assertTrue(startTime < System.currentTimeMillis());
        assertTrue(uptimeTime > 0);
    }
}
