package org.adventofcode.day10;

public record JoltageRequirements(double[] joltages) {

    // Defensive copy and validation
    public JoltageRequirements {
        if (joltages == null || joltages.length == 0) {
            throw new IllegalArgumentException("Joltages cannot be null or empty");
        }
        joltages = joltages.clone();
    }

    // Defensive copy on access
    @Override
    public double[] joltages() {
        return joltages.clone();
    }

    public int size() {
        return joltages.length;
    }
}
