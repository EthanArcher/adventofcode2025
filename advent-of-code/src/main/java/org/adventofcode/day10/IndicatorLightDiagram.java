package org.adventofcode.day10;

import java.util.Arrays;

public record IndicatorLightDiagram(char[] lights) {

    private static final char ON = '#';
    private static final char OFF = '.';

    // Defensive copy to prevent external modification
    public IndicatorLightDiagram {
        lights = lights.clone();
    }

    // Defensive copy on access
    @Override
    public char[] lights() {
        return lights.clone();
    }

    public IndicatorLightDiagram pressButton(ButtonWiringSchematic buttonWiringSchematic) {
        char[] newLights = lights.clone();
        for (int position : buttonWiringSchematic.getWiringPositions()) {
            newLights[position] = (newLights[position] == ON) ? OFF : ON;
        }
        return new IndicatorLightDiagram(newLights);
    }

    public boolean matches(IndicatorLightDiagram other) {
        return Arrays.equals(this.lights, other.lights);
    }

    public int size() {
        return lights.length;
    }

}
