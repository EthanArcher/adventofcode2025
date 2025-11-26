package org.adventofcode.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Utilities {

    public static List<String> readFileToListOfStrings(String fileName) {
        return getResourceAsString(fileName + ".txt");
    }

    private static List<String> getResourceAsString(String resource) {
        try {
            return Files.readAllLines(getResource(resource).toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static File getResource(String path) {
        return new File("src/main/resources/" + path);
    }
}
