package org.adventofcode.utils;

import java.util.Arrays;
import java.util.List;

public abstract class Day {

    private final String day;
    private final InputFileType inputFileType;

    public abstract long[] run();

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

    public char[][] readFileAsGrid() {
        List<String> lines = readFile();
        if (lines.isEmpty()) return new char[0][0];
        int maxLen = lines.stream().mapToInt(String::length).max().orElse(0);
        char[][] grid = new char[lines.size()][maxLen];
        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            for (int col = 0; col < maxLen; col++) {
                grid[row][col] = col < line.length() ? line.charAt(col) : ' ';
            }
        }
        return grid;
    }

    public char[][] deepCopyGrid(char[][] grid) {
        char[][] copy = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return copy;
    }

}
