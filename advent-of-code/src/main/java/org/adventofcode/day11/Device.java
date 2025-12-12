package org.adventofcode.day11;

import java.util.List;

public record Device(String id, List<String> connections) {

    public Device(String line) {
        this(parseId(line), parseConnections(line));
    }

    private static String parseId(String line) {
        return line.split(":")[0].trim();
    }

    private static List<String> parseConnections(String line) {
        String[] parts = line.split(":");
        return parts.length > 1 ? List.of(parts[1].trim().split("\\s+")) : List.of();
    }
}
