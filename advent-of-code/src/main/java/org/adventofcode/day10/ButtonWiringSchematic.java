package org.adventofcode.day10;

public record ButtonWiringSchematic(int[] wiring) {

    // Defensive copy and validation
    public ButtonWiringSchematic {
        if (wiring == null) {
            throw new IllegalArgumentException("Wiring cannot be null");
        }
        wiring = wiring.clone();
        // Validate no negative positions
        for (int position : wiring) {
            if (position < 0) {
                throw new IllegalArgumentException("Wiring positions must be non-negative");
            }
        }
    }

    // Defensive copy on access
    @Override
    public int[] wiring() {
        return wiring.clone();
    }

    int[] getWiringPositions() {
        return wiring.clone();
    }

    double[] wiringJoltageChangers(int size) {
        double[] joltageChangers = new double[size];
        for (int position : wiring) {
            if (position < size) {
                joltageChangers[position] = 1.0;
            }
        }
        return joltageChangers;
    }

}
