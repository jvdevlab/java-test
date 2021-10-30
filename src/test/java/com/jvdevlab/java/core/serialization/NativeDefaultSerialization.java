package com.jvdevlab.java.core.serialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.Test;

import lombok.Data;

// https://stackoverflow.com/questions/13895867/why-does-writeobject-throw-java-io-notserializableexception-and-how-do-i-fix-it
// "serializing such an inner class instance will result in serialization of its associated outer class instance as well"
@SuppressWarnings("unused")
public class NativeDefaultSerialization implements Serializable {

    private long serialVersionUID = 1L;

    @Test
    public void serializeAndDeserialize() throws IOException, ClassNotFoundException {

        class SimpleClass implements Serializable {
            private int value;
            private long serialVersionUID = 1L;

            public SimpleClass(int value) {
                this.value = value;
            }
        }

        SimpleClass serialized = new SimpleClass(1);

        // Serialize
        String fileName = this.getClass().getResource("/").getPath() + "SimpleClass.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        try (fileOutputStream; objectOutputStream) {
            objectOutputStream.writeObject(serialized);
        }

        SimpleClass deserialized;
        // Deserialize
        FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try (fileInputStream; objectInputStream) {
            deserialized = (SimpleClass) objectInputStream.readObject();
        }

        assertNotEquals(serialized, deserialized);
        assertEquals(serialized.value, deserialized.value);
    }
}
