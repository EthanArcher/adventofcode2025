package org.adventofcode.day08;

public record PositionInSpace(int x, int y, int z) {

    public static PositionInSpace toPositionInSpace(String positionString) {
        String[] parts = positionString.split(",");
        return new PositionInSpace(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])
        );
    }

}
