package com.jvdevlab.java.jvm.diagnostics.api;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jdk.jfr.consumer.RecordingStream;
import lombok.extern.slf4j.Slf4j;
import java.lang.Thread;

@Slf4j
public class JFREventStreaming {

    // Since Java 14 - JEP 349: JFR Event Streaming
    // https://openjdk.java.net/jeps/349
    // Here some profiles that has event names:
    // C:\tools\OpenJDK14U-jre_x64_windows_hotspot_14_36\jdk-14+36-jre\lib\jfr
    // Not much info on the web but here some good one
    // https://mbien.dev/blog/entry/jfr-event-streaming-with-java
    @Test
    public void streamCPULoadEvents() throws Exception {
        final String expected = "Test JFR monitoring exceptions";

        try (var rs = new RecordingStream()) {
            int secondsToRun = 2;
            Instant endTime = Instant.now().plusSeconds(secondsToRun);
            rs.setEndTime(endTime);

            rs.enable("jdk.JavaExceptionThrow").withPeriod(Duration.ofSeconds(1));

            rs.onEvent("jdk.JavaExceptionThrow", event -> {
                log.debug(event.toString());
                assertEquals(expected, event.getString("message"));
            });

            rs.startAsync();

            for (int i = 0; i < 5; i++) {
                try {
                    throw new NullPointerException(expected);
                } catch (Exception e) {
                    // do nothing
                }
            }

            Thread.sleep(secondsToRun * 1000);
        }
    }
}
