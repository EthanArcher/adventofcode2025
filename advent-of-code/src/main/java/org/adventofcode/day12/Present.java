package org.adventofcode.day12;

public record Present(int id, char[][] shape) {

    public int getArea() {
        int count = 0;
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[row].length; col++) {
                if (shape[row][col] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

}
