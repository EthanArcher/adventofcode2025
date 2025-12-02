package org.adventofcode.utils;

import java.util.List;

public abstract class Day {

    private final String day;
    private final InputFileType inputFileType;

    public abstract void run();

    public Day(String day, InputFileType inputFileType) {
        this.day = day;
        this.inputFileType = inputFileType;
    }

    public List<String> readFile() {
        return Utilities.readFileToListOfStrings(inputFileType.getPath() + day);
    }

    public String readFileAsString() {
        return Utilities.readFileToString(inputFileType.getPath() + day);
    }

}
