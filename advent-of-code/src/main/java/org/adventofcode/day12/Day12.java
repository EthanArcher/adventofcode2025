package org.adventofcode.day12;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.List;

public class Day12 extends Day {

    private static final String DAY = "day12";

    public Day12(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {
        List<List<String>> lines = readFileInSections();
        List<Present> presents = getPresents(lines);
        List<Requirement> requirements = getRequirements(lines);

        int validTrees1 = checkPerfectTessellation(requirements, presents);
        System.out.println("Valid trees (area check with perfect tesselation): " + validTrees1);

        int validTrees2 = checkTheoreticalMax(requirements);
        System.out.println("Valid trees (theoretical max check if only present 1 used): " + validTrees2);

        int validTrees3 = checkMinAreaNeeded(requirements);
        System.out.println("Valid trees (min area needed check if only present 1 used): " + validTrees3);

        int validTrees4 = checkLyingStanding(requirements);
        System.out.println("Valid trees (lying/standing check) if only present 1 used): " + validTrees4);

        int validTrees5 = checkNoTessellation(requirements);
        System.out.println("Valid trees (own 3x3 areas check, no tesselation): " + validTrees5);

        return new long[]{validTrees1, 0};

    }

    /**
     * Check if presents can fit with perfect tessellation by comparing total areas.
     */
    private int checkPerfectTessellation(List<Requirement> requirements, List<Present> presents) {
        int validTrees = 0;
        for (Requirement requirement : requirements) {
            int areaOfRequirement = requirement.rows() * requirement.cols();
            int areaOfSuggestedPresents = 0;

            for (int p = 0; p < requirement.presentCounts().length; p++) {
                int count = requirement.presentCounts()[p];
                Present present = presents.get(p);
                areaOfSuggestedPresents += count * present.getArea();
            }

            if (areaOfRequirement > areaOfSuggestedPresents) {
                validTrees++;
            }
        }
        return validTrees;
    }

    /**
     * Check theoretical maximum based on width and height minus borders.
     * The max number you can fit horizontally is width - 2.
     * The max number you can fit vertically is height - 2.
     */
    private int checkTheoreticalMax(List<Requirement> requirements) {
        int validTrees = 0;
        for (Requirement requirement : requirements) {
            int totalPresents = getTotalPresents(requirement);
            int height = requirement.rows();
            int width = requirement.cols();
            int presentsInHeight = height - 2;
            int presentsInWidth = width - 2;

            if (totalPresents < presentsInHeight * presentsInWidth) {
                validTrees++;
            }
        }
        return validTrees;
    }

    /**
     * Check if minimum area is sufficient.
     * The best tessellation uses 4x3 squares that fit 2 presents each.
     * So for n presents you need at least 6n area.
     */
    private int checkMinAreaNeeded(List<Requirement> requirements) {
        int validTrees = 0;
        for (Requirement requirement : requirements) {
            int totalPresents = getTotalPresents(requirement);
            int areaOfRequirement = requirement.rows() * requirement.cols();
            int minAreaNeeded = totalPresents * 6;

            if (minAreaNeeded <= areaOfRequirement) {
                validTrees++;
            }
        }
        return validTrees;
    }

    /**
     * Check if presents can fit using 4x3 or 3x4 tessellation patterns.
     * Each 4x3 block can fit 2 presents lying down.
     * Each 3x4 block can fit 2 presents standing up.
     */
    private int checkLyingStanding(List<Requirement> requirements) {
        int validTrees = 0;
        for (Requirement requirement : requirements) {
            int totalPresents = getTotalPresents(requirement);
            int height = requirement.rows();
            int width = requirement.cols();

            int presentsLyingDown = (height / 3) * (width / 4) * 2;
            int presentsStandingUp = (height / 4) * (width / 3) * 2;

            if (totalPresents <= presentsLyingDown || totalPresents <= presentsStandingUp) {
                validTrees++;
            }
        }
        return validTrees;
    }

    /**
     * Check if presents can fit assuming no tessellation (each needs own 3x3 area).
     */
    private int checkNoTessellation(List<Requirement> requirements) {
        int validTrees = 0;
        for (Requirement requirement : requirements) {
            int totalPresents = getTotalPresents(requirement);
            int height = requirement.rows();
            int width = requirement.cols();
            int wide = width / 3;
            int high = height / 3;

            if (totalPresents <= wide * high) {
                validTrees++;
            }
        }
        return validTrees;
    }

    private int getTotalPresents(Requirement requirement) {
        int totalPresents = 0;
        for (int count : requirement.presentCounts()) {
            totalPresents += count;
        }
        return totalPresents;
    }

    private List<Present> getPresents(List<List<String>> lines) {
        return lines.subList(0, lines.size() - 1).stream()
                .map(this::parsePresent)
                .toList();
    }

    private static List<Requirement> getRequirements(List<List<String>> lines) {
        return lines.get(lines.size() - 1).stream()
                .map(line -> {
                    String[] parts = line.split(":");
                    String[] sizes = parts[0].split("x");

                    int rows = Integer.parseInt(sizes[0].trim());
                    int cols = Integer.parseInt(sizes[1].trim());

                    String[] countPartsString = parts[1].trim().split(" ");
                    int[] countParts = new int[countPartsString.length];
                    for (int i = 0; i < countPartsString.length; i++) {
                        countParts[i] = Integer.parseInt(countPartsString[i]);
                    }

                    return new Requirement(rows, cols, countParts);
                })
                .toList();
    }

    private Present parsePresent(List<String> presentString) {
        int id = Integer.parseInt(presentString.get(0).split(":")[0].trim());
        List<String> shapeLines = presentString.subList(1, presentString.size());
        char[][] shape = shapeLines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return new Present(id, shape);
    }
}
