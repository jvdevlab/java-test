package com.jvdevlab.java.core.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.Test;

import lombok.Data;

// https://stackoverflow.com/questions/13895867/why-does-writeobject-throw-java-io-notserializableexception-and-how-do-i-fix-it
// "serializing such an inner class instance will result in serialization of its associated outer class instance as well"
@SuppressWarnings("unused")
public class CustomSerialization implements Serializable {

    private long serialVersionUID = 1L;

    @Test
    public void serializeAndDeserialize() throws IOException, ClassNotFoundException {

        class SimpleClass implements Externalizable {

            private int intValue;
            private String stringValue;

            private long serialVersionUID = 1L;

            public SimpleClass(int intValue, String stringValue) {
                this.intValue = intValue;
                this.stringValue = stringValue;
            }

            @Override
            public void writeExternal(ObjectOutput out) throws IOException {
                out.writeInt(intValue);
                out.writeUTF(stringValue);
            }

            @Override
            public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
                // Must be in the same order as writeExternal
                intValue = in.readInt();
                stringValue = in.readUTF();
            }
        }

        SimpleClass serialized = new SimpleClass(1, "a");

        // Serialize
        String fileName = this.getClass().getResource("/").getPath() + "SimpleClass.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        try (fileOutputStream; objectOutputStream) {
            serialized.writeExternal(objectOutputStream);
        }

        SimpleClass deserialized = new SimpleClass(0, null);
        // Deserialize
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try (fileInputStream; objectInputStream) {
            deserialized.readExternal(objectInputStream);
        }

        assertNotEquals(serialized, deserialized);
        assertEquals(serialized.intValue, deserialized.intValue);
        assertEquals(serialized.stringValue, deserialized.stringValue);
    }
}
