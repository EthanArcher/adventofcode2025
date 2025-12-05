package org.adventofcode.day05;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day05 extends Day {

    private static final String DAY = "day05";

    public Day05(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        List<List<String>> readFileInSections = readFileInSections();

        List<Range> ranges = readFileInSections.get(0).stream()
                .map(Day05::parseRange)
                .collect(Collectors.toCollection(ArrayList::new));

        List<Long> availableIds = readFileInSections.get(1).stream()
                .map(Long::parseLong)
                .toList();

        int freshCount = 0;
        for (long id : availableIds) {
            for (Range range : ranges) {
                if (range.isInRange(id)) {
                    freshCount++;
                    break;
                }
            }
        }

        System.out.println("Part 1: " + freshCount);

        // Part 2 plan:
        // sort all the ranges
        // squash them
        ranges.sort(Comparator.comparing(Range::start));

        List<Range> squashedRange = new ArrayList<>();

        for (Range range : ranges) {
            if (squashedRange.isEmpty()) {
                squashedRange.add(range);
            } else {
                if (range.start() <= squashedRange.getLast().end() + 1) {
                    Range expandedRange = new Range(squashedRange.getLast().start(), Math.max(range.end(), squashedRange.getLast().end()));
                    squashedRange.removeLast();
                    squashedRange.add(expandedRange);
                } else {
                    squashedRange.add(range);
                }
            }
        }

        long totalSpreadCovered = squashedRange.stream().mapToLong(r -> (r.end() - r.start() + 1)).sum();

        System.out.println("Part 2: " + totalSpreadCovered);

        return new long[]{freshCount, totalSpreadCovered};
    }

    private static Range parseRange(String line) {
        String[] range = line.split("-");
        return new Range(Long.parseLong(range[0]), Long.parseLong(range[1]));
    }
}

record Range(long start, long end) {

    boolean isInRange(long number) {
        return number >= start && number <= end;
    }

}
