package org.adventofcode.day09;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Day09 extends Day {

    private static final String DAY = "day09";

    private Set<Position2D> positionsOutside = new HashSet<>();

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
        PriorityQueue<TiledArea> tiledAreasAllInside = new PriorityQueue<>((a, b) -> Long.compare(b.area(), a.area()));
        Set<Position2D> greenTiles = new HashSet<>();

        for (int i = 0; i < positions.size(); i++) {
            Position2D pos1 = positions.get(i);
            Position2D pos2 = positions.get((i + 1) % positions.size());

            int dx = Math.abs(pos2.x() - pos1.x()) + 1;
            int dy = Math.abs(pos2.y() - pos1.y()) + 1;

            for (int x = Math.min(pos1.x(), pos2.x()); x < Math.min(pos1.x(), pos2.x()) + dx; x++) {
                for (int y = Math.min(pos1.y(), pos2.y()); y < Math.min(pos1.y(), pos2.y()) + dy; y++) {
                    greenTiles.add(new Position2D(x, y));
                }
            }
            greenTiles.add(positions.get(i));
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
            if (allRectangleEdgesInside(tiledArea.position1(), tiledArea.position2(), positions, greenTiles)) {
                largestTiledAreaInside = tiledArea;
                break;
            }
        }

        System.out.println(largestTiledAreaInside);

        return new long[]{largestTiledArea.area(), largestTiledAreaInside.area()};
    }


    private boolean allRectangleEdgesInside(Position2D start, Position2D end, List<Position2D> polygon, Set<Position2D> greenTiles) {
        int x1 = Math.min(start.x(), end.x());
        int x2 = Math.max(start.x(), end.x());
        int y1 = Math.min(start.y(), end.y());
        int y2 = Math.max(start.y(), end.y());

        // Check corners first
        if (!isPointInsidePolygon(new Position2D(start.x(), end.y()), polygon, greenTiles)) return false;
        if (!isPointInsidePolygon(new Position2D(end.x(), start.y()), polygon, greenTiles)) return false;

        // Top edge
        for (int x = x1; x <= x2; x++) {
            if (!isPointInsidePolygon(new Position2D(x, y1), polygon, greenTiles)) {
                return false;
            }
        }
        // Bottom edge
        for (int x = x1; x <= x2; x++) {
            if (!isPointInsidePolygon(new Position2D(x, y2), polygon, greenTiles)) {
                return false;
            }
        }
        // Left edge
        for (int y = y1; y <= y2; y++) {
            if (!isPointInsidePolygon(new Position2D(x1, y), polygon, greenTiles)) {
                return false;
            }
        }
        // Right edge
        for (int y = y1; y <= y2; y++) {
            if (!isPointInsidePolygon(new Position2D(x2, y), polygon, greenTiles)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPointInsidePolygon(Position2D point, List<Position2D> polygon, Set<Position2D> greenTiles) {
        if (positionsOutside.contains(point)) {
            return false;
        }

        int crossings = 0;
        int n = polygon.size();
        int px = point.x();
        int py = point.y();

        for (int i = 0; i < n; i++) {
            Position2D v1 = polygon.get(i);
            Position2D v2 = polygon.get((i + 1) % n);

            int x1 = v1.x();
            int y1 = v1.y();
            int x2 = v2.x();
            int y2 = v2.y();

            if (greenTiles.contains(point)) {
                return true;
            }

            if (((y1 > py) != (y2 > py))) {
                double xCross = x1 + (double) (py - y1) * (x2 - x1) / (y2 - y1);
                if (px < xCross) {
                    crossings++;
                }
            }
        }
        if ((crossings % 2) != 1) {
            positionsOutside.add(point);
        }

        return (crossings % 2) == 1;
    }


}
