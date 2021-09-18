package com.jvdevlab.java.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThisInLambda {

    @Test
    public void thisInLambda() {
        class A {
            int i = 1;

            int getI() {
                return ((Supplier<Integer>) (() -> {
                    int i = 2;
                    return this.i;
                })).get();
            }
        }

        assertEquals(1, new A().getI());
    }

    @Test
    public void captureLambda() {
        class A {
            int i = 0;

            A(int i) {
                this.i = i;
            }

            Supplier<Integer> getFunction() {
                return (() -> {
                    log.debug("Called Lambda.");
                    return this.i * 2;
                });
            }
        }

        Supplier<Integer> func = new A(2).getFunction();
        log.debug("'A' went out of scope."); // But this.i is captured by closure!

        assertEquals(4, func.get());
    }

}
