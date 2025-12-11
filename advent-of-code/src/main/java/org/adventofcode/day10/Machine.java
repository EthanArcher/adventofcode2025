package org.adventofcode.day10;

import java.util.List;

public record Machine(
        IndicatorLightDiagram indicatorLightDiagram,
        List<ButtonWiringSchematic> buttonWiringSchematics,
        JoltageRequirements joltageRequirements) {

    // Defensive copy for list and validation
    public Machine {
        if (indicatorLightDiagram == null || buttonWiringSchematics == null || joltageRequirements == null) {
            throw new IllegalArgumentException("Machine components cannot be null");
        }
        buttonWiringSchematics = List.copyOf(buttonWiringSchematics);
    }

    double[][] getButtonMatrix() {
        int joltageCount = joltageRequirements.size();
        int buttonCount = buttonWiringSchematics.size();

        double[][] matrix = new double[joltageCount][buttonCount];

        for (int buttonIndex = 0; buttonIndex < buttonCount; buttonIndex++) {
            double[] buttonEffects = buttonWiringSchematics.get(buttonIndex).wiringJoltageChangers(joltageCount);
            for (int joltageIndex = 0; joltageIndex < joltageCount; joltageIndex++) {
                matrix[joltageIndex][buttonIndex] = buttonEffects[joltageIndex];
            }
        }

        return matrix;
    }

}
