package com.jvdevlab.java.functional.lambda;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.junit.jupiter.api.Test;

public class LambdaVsAnonymousClass {

    String name = "name 0";

    @Test
    @SuppressWarnings("unused")
    public void lambdaVsAnonymousClass() {

        String name = "name 2";
        String nonFinal;

        Externalizable anonymous = new Externalizable() {

            // Can shadow
            String name = "name 3";

            @Override
            public void writeExternal(ObjectOutput out) throws IOException {
                // Can't use non final
                // nonFinal = "something";

                // 'this' points to itself
                out.writeUTF(this.name);
            }

            // Can implement more than one method
            @Override
            public void readExternal(ObjectInput in)
                    throws IOException, ClassNotFoundException {
            }

        };

        Runnable r = () -> {
            // Can't shadow
            // String name = "name3";

            // Can't use non final
            // nonFinal = "something";

            // 'this' point to surrounding this which points to the current
            // object, as such below will be = name 0.
            System.out.println(this.name);
        };

    }

}
