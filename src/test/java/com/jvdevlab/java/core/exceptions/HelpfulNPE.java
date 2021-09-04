package com.jvdevlab.java.core.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Preview since Java 14 JEP 358: Helpful NullPointerExceptions
 * https://openjdk.java.net/jeps/358
 *
 * See for full test
 * http://cr.openjdk.java.net/~goetz/wr19/8218628-exMsg-NPE/21-incremental/test/
 * hotspot/jtreg/runtime/exceptionMsgs/NullPointerException/
 * NullPointerExceptionTest.java.html
 *
 * "The null-detail message is switched off per default and can be enabled by
 * command-line option -XX:+ShowCodeDetailsInExceptionMessages. There is no way
 * to specify that only some NPE-raising bytecodes are of interest. For the
 * following reasons the null-detail message might not be wanted in all
 * circumstances: 1. Performance ... 2. Security ... 3. Compatibility ... We
 * intend to enable the null-detail message by default in a future release."
 *
 *
 * HOW TO RUN:
 *
 * UPDATE: As of 04/01 supported by redhat.java and as such vscode-java-test so
 * no need to run via maven. For this you need to configure
 * java-tests\.vscode\settings.json to pass
 * -XX:+ShowCodeDetailsInExceptionMessages
 *
 *
 * All you need is to add -XX:+ShowCodeDetailsInExceptionMessages to POM
 * surefire plugin section. But I somehow managed to go down the rabbit hole and
 * came up with other options as initial solutions didn't work. I will keep it
 * documented below as it was educational.
 *
 * Option 1: Didn't work.
 *
 * I'v added -XX:+ShowCodeDetailsInExceptionMessages to MAVEN_OPTS but the test
 * were still running with empty message for NPE. It didn't work because these
 * are options for maven not for surfier plugin that runs tests!
 *
 * Option 2: Didn't work (requires main() method that calls the test)
 *
 * This
 * https://stackoverflow.com/questions/9846046/run-main-class-of-maven-project
 * command line and plugin version didn't work. There were two issues: 1)
 * ClassNotFound that at least for command line was resolved by adding
 * -Dexec.classpathScope=test 2) compiler error --enable-preview is not enabled.
 * https://stackoverflow.com/questions/60922845/maven-exec-plugin-with-preview-
 * features explains that "exec:java runs in the same maven java process, which
 * by default isn't started with --enable-preview" The is a solution with using
 * .mvn/jvm.config file but the below is better.
 *
 * Option 3: Works. (requires main() method that calls the test)
 *
 * mvn compile exec:exec -Dexec.executable="java" -Dexec.args="--enable-preview
 * -XX:+ShowCodeDetailsInExceptionMessages -classpath %classpath
 * com.vj.java.core.exceptions.HelpfulNPE" -Dexec.classpathScope=test
 */
@Slf4j
public class HelpfulNPE {

    class Obj {
        Obj b;
        Obj c;

        public Obj f() {
            return null;
        };
    }

    @Test
    @SuppressWarnings("null")
    public void detailedNPEMessageAssign() {
        assertThrows(NullPointerException.class, () -> {
            try {
                Obj a = null;
                a.b = new Obj();
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                assertEquals("Cannot assign field \"b\" because \"a\" is null", e.getMessage());
                throw e;
            }
        });

        assertThrows(NullPointerException.class, () -> {
            try {
                Obj a = new Obj();
                a.b = null;
                a.b.c = new Obj();
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                assertEquals("Cannot assign field \"c\" because \"a.b\" is null", e.getMessage());
                throw e;
            }
        });

    }

    @SuppressWarnings("null")
    public void detailedNPEMessageRead() {
        try {
            Obj a = new Obj();
            Obj b = null;
            a.c = b.c;
        } catch (NullPointerException e) {
            log.debug(e.getMessage());
            assertEquals("Cannot read field \"c\" because \"b\" is null", e.getMessage());
        }
    }

    @Test
    public void detailedNPEMessageStore() {
        assertThrows(NullPointerException.class, () -> {
            try {
                Obj[][][] a = new Obj[1][1][1];
                a[0][0] = null;
                a[0][0][0] = new Obj();
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                assertEquals("Cannot store to object array because \"a[0][0]\" is null", e.getMessage());
                throw e;
            }
        });
    }

    @Test
    public void detailedNPEMessageLoad() {
        assertThrows(NullPointerException.class, () -> {
            try {
                Obj[][][] a = new Obj[1][1][1];
                a[0] = null;
                @SuppressWarnings("unused")
                Obj b = a[0][0][0];
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                assertEquals("Cannot load from object array because \"a[0]\" is null", e.getMessage());
                throw e;
            }
        });

        // This might look like assigment but before assigning we need to read a[0]
        assertThrows(NullPointerException.class, () -> {
            try {
                Obj[][][] a = new Obj[1][1][1];
                a[0] = null;
                a[0][0][0] = new Obj();
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                assertEquals("Cannot load from object array because \"a[0]\" is null", e.getMessage());
                throw e;
            }
        });
    }

    @Test
    public void detailedNPEMessageInvoke() {
        assertThrows(NullPointerException.class, () -> {
            try {
                new Obj().f().f();
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                // Cannot invoke "...HelpfulNPE$Obj.f()" because
                // the return value of "...HelpfulNPE$Obj.f()" is null
                assertTrue(e.getMessage().contains("$Obj.f()"));
                throw e;
            }
        });
    }

    // "Only NPEs that are created and thrown directly by the JVM will include the
    // null-detail message. NPEs that are explicitly created and/or explicitly
    // thrown by programs running on the JVM are not subject to the bytecode
    // analysis and null-detail message creation described below."
    @Test
    public void noDetailedMessageForExplicitNPE() {
        assertThrows(NullPointerException.class, () -> {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                log.debug(e.getMessage());
                assertEquals(null, e.getMessage());
                throw e;
            }
        });
    }

}
