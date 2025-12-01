package org.adventofcode.utils;

public enum InputFileType {
    EXAMPLE("example/"),
    ACTUAL("actual/");

    private final String path;

    InputFileType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
