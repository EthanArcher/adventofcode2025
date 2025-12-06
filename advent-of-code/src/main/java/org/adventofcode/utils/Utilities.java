package org.adventofcode.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static void appendToHashMap(HashMap<Integer, List<String>> map, int key, String value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<String> newList = new ArrayList<>();
            newList.add(value);
            map.put(key, newList);
        }
    }
}
