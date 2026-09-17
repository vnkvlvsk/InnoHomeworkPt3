package org.kavaleuski.parking.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class ResourceReader {

    private ResourceReader() {
    }

    public static List<String> readLines(String resourceName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (InputStream input = resourceStream(resourceName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static InputStream resourceStream(String resourceName) throws IOException {
        InputStream input = ResourceReader.class.getClassLoader().getResourceAsStream(resourceName);
        if (input == null) {
            throw new IOException("Resource not found on classpath: " + resourceName);
        }
        return input;
    }
}
