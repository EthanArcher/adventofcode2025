package org.adventofcode.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Utilities {

    public static List<String> readFileToListOfStrings(String fileName) {
        return readLinesFromResource(fileName + ".txt");
    }

    public static String readFileToString(String fileName) {
        try {
            return Files.readString(getResourcePath(fileName + ".txt"));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read file: " + fileName, e);
        }
    }

    private static List<String> readLinesFromResource(String resource) {
        try {
            return Files.readAllLines(getResourcePath(resource));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read lines from: " + resource, e);
        }
    }

    private static Path getResourcePath(String path) {
        return Path.of("src/main/resources", path);
    }
}
