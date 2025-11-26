package org.adventofcode.utils;

import java.util.List;

public class Day {

    private final String day;

    public Day(String day) {
        this.day = day;
    }

    public List<String> readFile() {
        return Utilities.readFileToListOfStrings(day);
    }
}
