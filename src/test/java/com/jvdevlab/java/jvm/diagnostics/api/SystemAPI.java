package com.jvdevlab.java.jvm.diagnostics.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemAPI {

    @Test
    public void currentSystemProperties() {
        log.debug(System.lineSeparator() + "Current System Properties:");
        Properties props = System.getProperties();
        props.forEach((k, v) -> log.debug(k + ": " + v));
        assertNotNull(props);
    }

}
