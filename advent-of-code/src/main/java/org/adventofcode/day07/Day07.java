package org.adventofcode.day07;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.*;

public class Day07 extends Day {

    private static final String DAY = "day07";

    public Day07(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        char[][] input = readFileAsGrid();
        Position start = null;
        HashMap<Position, Long> beamsAtPosition = new HashMap<>();
        HashMap<Integer, List<Integer>> splitterMap = new HashMap<>();

        for (int row = 0; row < input.length; row++) {
            List<Integer> splittersForRow = new ArrayList<>();
            for (int col = 0; col < input[row].length; col++) {
                char currentChar = input[row][col];
                if (currentChar == 'S') {
                    start = new Position(row, col);
                    beamsAtPosition.put(new Position(row, col), 1L);
                } else if (currentChar == '^') {
                    splittersForRow.add(col);
                }
            }
            splitterMap.put(row, splittersForRow);
        }

        if (start == null) {
            throw new IllegalStateException("Start position not found in input.");
        }

        Set<Position> beams = new HashSet<>();
        beams.add(start);
        int splitCount = 0;
        for (int row = 1; row < input.length; row++) {
            Set<Position> splitBeams = new HashSet<>();
            for (Position beam : beams) {
                long beamValue = beamsAtPosition.get(beam);
                if (splitterMap.containsKey(row) && splitterMap.get(row).contains(beam.col())) {
                    // Splitter found on line - create new beams
                    Position left = new Position(beam.row() + 1, beam.col() - 1);
                    Position right = new Position(beam.row() + 1, beam.col() + 1);
                    splitBeams.add(left);
                    splitBeams.add(right);

                    beamsAtPosition.merge(left, beamValue, Long::sum);
                    beamsAtPosition.merge(right, beamValue, Long::sum);

                    splitCount++;
                } else {
                    // No splitter - continue straight down
                    splitBeams.add(new Position(beam.row() + 1, beam.col()));
                    beamsAtPosition.merge(new Position(beam.row() + 1, beam.col()), beamValue, Long::sum);

                }
            }
            if (!splitBeams.isEmpty()) {
                beams = splitBeams;
            }
        }

        System.out.println("Part 1: " + splitCount);

        long counter = beamsAtPosition.entrySet()
                .stream()
                .filter(entry -> entry.getKey().row() == input.length - 1)
                .mapToLong(Map.Entry::getValue)
                .sum();

        System.out.println("Part 2: " + counter);

        return new long[]{splitCount, counter};
    }
}

record Position(int row, int col) {
}
