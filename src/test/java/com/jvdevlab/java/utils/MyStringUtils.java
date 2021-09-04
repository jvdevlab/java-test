package com.jvdevlab.java.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MyStringUtils {

    public MyStringUtils() {
        throw new UnsupportedOperationException();
    }

    public static String inputStreamToString(InputStream in) {
        return new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
    }
}
