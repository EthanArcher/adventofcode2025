package org.adventofcode.day10;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.*;

import static org.adventofcode.day10.ILP.solveWithILP;

public class Day10 extends Day {

    private static final String DAY = "day10";

    public Day10(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        List<String> input = readFileAsLines();
        List<Machine> machines = new ArrayList<>();

        for (String line : input) {
            // IndicatorLightDiagram
            String part1 = line.split("]")[0];
            IndicatorLightDiagram indicatorLightDiagram = new IndicatorLightDiagram(part1.substring(1).toCharArray());

            // ButtonWiringSchematics
            String part2 = line.split("]")[1].split("\\{")[0];
            List<ButtonWiringSchematic> buttonWiringSchematics = Arrays.stream(part2.split("\\("))
                    .map(s -> s.replace(")", "").trim())
                    .filter(s -> !s.isEmpty())
                    .map(s -> Arrays.stream(s.split(","))
                            .map(String::trim)
                            .filter(v -> !v.isEmpty())
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .map(arr -> new ButtonWiringSchematic(arr))
                    .toList();

            // JoltageRequirements
            String part3 = line.split("\\{")[1];
            String[] joltages = part3.substring(0, part3.length() - 1).split(",");
            double[] joltagesInt = Arrays.stream(joltages).mapToDouble(Double::parseDouble).toArray();
            JoltageRequirements joltageRequirements = new JoltageRequirements(joltagesInt);

            // Process each line of input
            Machine machine = new Machine(indicatorLightDiagram, buttonWiringSchematics, joltageRequirements);
            machines.add(machine);
        }

        int part1 = 0;
        for (Machine machine : machines) {
            int presses = pressButtonsOnTheMachineUntilItMatches(machine);
//            System.out.println("Machine requires " + presses + " presses to match the target diagram.");
            part1 += presses;
        }

        System.out.println("Part 1: " + part1);

        int part2 = 0;
        for (Machine machine : machines) {
            int presses = calculateMinimumButtonPressesForJoltages(machine);
//            System.out.println("Machine requires " + presses + " presses to meet joltage requirements.");
            part2 += presses;
        }
        System.out.println("Part 2: " + part2);

        return new long[]{part1, part2};
    }

    private int pressButtonsOnTheMachineUntilItMatches(Machine machine) {
        char[] startingLights = new char[machine.indicatorLightDiagram().lights().length];
        Arrays.fill(startingLights, '.');
        IndicatorLightDiagram starting = new IndicatorLightDiagram(startingLights);

        boolean found = false;
        int presses = 0;
        HashMap<IndicatorLightDiagram, Integer> seenPositions = new HashMap<>();
        seenPositions.put(starting, 0);

        while (!found) {
            HashMap<IndicatorLightDiagram, Integer> newPositions = new HashMap<>();

            for (IndicatorLightDiagram position : seenPositions.keySet()) {
                for (ButtonWiringSchematic buttonWiringSchematic : machine.buttonWiringSchematics()) {
                    IndicatorLightDiagram newPosition = position.pressButton(buttonWiringSchematic);
                    if (!lightSequenceHasBeenSeenBefore(newPosition, seenPositions.keySet()) && !lightSequenceHasBeenSeenBefore(newPosition, newPositions.keySet())) {
                        newPositions.put(newPosition, seenPositions.get(position) + 1);
                        if (newPosition.matches(machine.indicatorLightDiagram())) {
                            found = true;
//                            System.out.println("Found solution in " + (seenPositions.get(position) + 1) + " steps.");
                            presses = seenPositions.get(position) + 1;
                            break;
                        }
                    }
                }
                if (found) break;
            }
            seenPositions.putAll(newPositions);
        }

        return presses;
    }

    private boolean lightSequenceHasBeenSeenBefore(IndicatorLightDiagram indicatorLightDiagram, Set<IndicatorLightDiagram> seenPositions) {
        for (IndicatorLightDiagram seenPosition : seenPositions) {
            if (seenPosition.matches(indicatorLightDiagram)) {
                return true;
            }
        }
        return false;
    }

    private int calculateMinimumButtonPressesForJoltages(Machine machine) {
        double[][] coefficientMatrix = machine.getButtonMatrix();
        double[] targetJoltages = machine.joltageRequirements().joltages();

        int[] solution = solveWithILP(coefficientMatrix, targetJoltages);

        return Arrays.stream(solution).sum();
    }

}
