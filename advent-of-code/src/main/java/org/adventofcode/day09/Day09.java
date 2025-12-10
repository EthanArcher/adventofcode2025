package org.adventofcode.day09;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Day09 extends Day {

    private static final String DAY = "day09";

    public Day09(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        List<Position2D> positions = readFileAsLines().stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Position2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
                .toList();

        PriorityQueue<TiledArea> tiledAreas = new PriorityQueue<>((a, b) -> Long.compare(b.area(), a.area()));

        List<List<Position2D>> verticals = new ArrayList<>();
        List<List<Position2D>> horizontals = new ArrayList<>();

        for (int i = 0; i < positions.size(); i++) {
            Position2D pos1 = positions.get(i);
            Position2D pos2 = positions.get((i + 1) % positions.size());

            if (pos1.x() == pos2.x()) {
                // Vertical line
                verticals.add(List.of(pos1, pos2));
            } else if (pos1.y() == pos2.y()) {
                // Horizontal line
                horizontals.add(List.of(pos1, pos2));
            }
        }

        for (int i = 0; i < positions.size(); i++) {
            Position2D pos1 = positions.get(i);
            for (int j = i + 1; j < positions.size(); j++) {
                Position2D pos2 = positions.get(j);
                long dx = Math.abs(pos2.x() - pos1.x()) + 1;
                long dy = Math.abs(pos2.y() - pos1.y()) + 1;
                long area = Math.abs(dx * dy);
                tiledAreas.add(new TiledArea(pos1, pos2, area));
            }
        }

        TiledArea largestTiledArea = tiledAreas.peek();
        System.out.println(largestTiledArea);

        TiledArea largestTiledAreaInside = null;

        while (!tiledAreas.isEmpty()) {
            TiledArea tiledArea = tiledAreas.poll(); // removes and returns the largest area
            System.out.println(tiledArea.area());

            if (validRectangle(tiledArea.position1(), tiledArea.position2(), verticals, horizontals, positions)) {
                largestTiledAreaInside = tiledArea;
                break;
            }
        }

        System.out.println(largestTiledAreaInside);

        return new long[]{largestTiledArea.area(), largestTiledAreaInside.area()};
    }

    private boolean validRectangle(Position2D start, Position2D end, List<List<Position2D>> verticals, List<List<Position2D>> horizontals, List<Position2D> positions) {
        int left = Math.min(start.x(), end.x());
        int right = Math.max(start.x(), end.x());
        int top = Math.min(start.y(), end.y());
        int bottom = Math.max(start.y(), end.y());

        // Check vertical lines to see if any overlap with rectangle interior
        for (List<Position2D> line : verticals) {
            int xPosition = line.get(0).x();
            int yMin = Math.min(line.get(0).y(), line.get(1).y());
            int yMax = Math.max(line.get(0).y(), line.get(1).y());

            // Check if vertical line is strictly inside the rectangle (not on edges)
            if (xPosition > left && xPosition < right) {
                // Check if the line segment overlaps with rectangle's y-range
                if (Math.max(top, yMin) < Math.min(bottom, yMax)) {
                    System.out.println("Vertical boundary at x=" + xPosition + " overlaps rectangle");
                    return false;
                }
            }
        }

        // Check horizontal lines to see if any overlap with rectangle interior
        for (List<Position2D> line : horizontals) {
            int yPosition = line.get(0).y();
            int xMin = Math.min(line.get(0).x(), line.get(1).x());
            int xMax = Math.max(line.get(0).x(), line.get(1).x());

            // Check if horizontal line is strictly inside the rectangle (not on edges)
            if (yPosition > top && yPosition < bottom) {
                // Check if the line segment overlaps with rectangle's x-range
                if (Math.max(left, xMin) < Math.min(right, xMax)) {
                    System.out.println("Horizontal boundary at y=" + yPosition + " overlaps rectangle");
                    return false;
                }
            }
        }

        return true;
    }

}
