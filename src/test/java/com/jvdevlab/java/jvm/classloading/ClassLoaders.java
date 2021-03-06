package com.jvdevlab.java.jvm.classloading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassLoaders {

    @Test
    public void builtInClassLoaders() {
        ClassLoader application = this.getClass().getClassLoader();
        log.debug(application.toString());
        assertEquals("app", application.getName());
        Arrays.stream(application.getDefinedPackages())
                .forEach(p -> log.debug(p.getName()));
        log.debug("");

        ClassLoader platform = application.getParent();
        log.debug(platform.toString());
        assertEquals("platform", platform.getName());
        // Print packages loaded using Platform Class loader
        Arrays.stream(platform.getDefinedPackages())
                .forEach(p -> log.debug(p.getName()));

        // Bootstrap Class Loaded is native so result is null.
        ClassLoader bootstrap = platform.getParent();
        assertNull(bootstrap);
    }

    @Test
    public void customClassLoader() {
        // https://github.com/trung/InMemoryJavaCompiler
        // https://www.logicbig.com/tutorials/core-java-tutorial/java-se-compiler-api/compiler-api-memory-loader.html
        // Using javax.tools.JavaCompiler and custom class loader it is possible
        // to
        // compile and load classes in memory.
    }

    // https://stackoverflow.com/questions/1771679/difference-between-threads-context-class-loader-and-normal-classloader
    /*
     * Thread context class loaders provide a back door around the class loading
     * delegation scheme. Take JNDI for instance: its guts are implemented by
     * bootstrap classes in rt.jar (starting with J2SE 1.3), but these core JNDI
     * classes may load JNDI providers implemented by independent vendors and
     * potentially deployed in the application's -classpath. This scenario calls
     * for a parent class loader (the primordial one in this case) to load a
     * class visible to one of its child class loaders (the system one, for
     * example) (VJ: Note, in delegation algorithm the visibility works the
     * other way around). Normal J2SE delegation does not work, and the
     * workaround is to make the core JNDI classes use thread context loaders,
     * thus effectively "tunneling" through the class loader hierarchy in the
     * direction opposite to the proper delegation.
     */
    @Test
    public void doNotUseContextClassLoader() {
        ClassLoader contextClassLoader = Thread.currentThread()
                .getContextClassLoader();
        log.debug(contextClassLoader.toString());
        assertEquals("app", contextClassLoader.getName());
        assertEquals("platform", contextClassLoader.getParent().getName());
    }
}
