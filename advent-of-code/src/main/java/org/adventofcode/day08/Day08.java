package org.adventofcode.day08;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.*;

import static org.adventofcode.day08.PositionInSpace.toPositionInSpace;

public class Day08 extends Day {

    private static final String DAY = "day08";

    public Day08(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {
        return run(1);
    }

    public long[] run(int times) {

        List<String> input = readFileAsLines();

        List<PositionInSpace> positions = input.stream()
                .map(line -> {
                    String[] parts = line.split(",");
                    return toPositionInSpace(line);
                }).toList();

        TreeSet<ConnectedJunctionBoxes> connectedJunctionBoxes = new TreeSet<>(Comparator.comparingDouble(ConnectedJunctionBoxes::distance));
        HashMap<PositionInSpace, PositionInSpace> connectionsMap = new HashMap<>();
        Set<PositionInSpace> uniquePositions = new HashSet<>(positions);

        for (int i = 0; i < positions.size(); i++) {
            PositionInSpace pos1 = positions.get(i);
            connectionsMap.put(pos1, pos1);
            for (int j = i + 1; j < positions.size(); j++) {
                PositionInSpace pos2 = positions.get(j);
                double distance = findDistance(pos1, pos2);
                connectedJunctionBoxes.add(new ConnectedJunctionBoxes(pos1, pos2, distance));
            }
        }

        for (int i = 0; i < times; i++) {
            ConnectedJunctionBoxes connectedJunctionBox = connectedJunctionBoxes.getFirst();
            rewire(connectionsMap, connectedJunctionBox.start(), connectedJunctionBox.end(), uniquePositions);
            connectedJunctionBoxes.remove(connectedJunctionBox);
        }

        HashMap<PositionInSpace, Integer> finalCircuits = new HashMap<>();
        for (PositionInSpace key : connectionsMap.keySet()) {
            PositionInSpace root = connectionsMap.get(key);
            finalCircuits.put(root, finalCircuits.getOrDefault(root, 0) + 1);
        }

        List<Integer> sizes = finalCircuits.values().stream()
                .sorted(Comparator.reverseOrder())
                .toList();

        long part1 = (long) sizes.get(0) * sizes.get(1) * sizes.get(2);

        System.out.println("Part 1: " + part1);

        long part2 = 0;

        while (uniquePositions.size() > 1) {
            ConnectedJunctionBoxes connectedJunctionBox = connectedJunctionBoxes.getFirst();
            rewire(connectionsMap, connectedJunctionBox.start(), connectedJunctionBox.end(), uniquePositions);
            connectedJunctionBoxes.remove(connectedJunctionBox);
            part2 = (long) connectedJunctionBox.start().x() * connectedJunctionBox.end().x();
        }

        System.out.println("Part 2: " + part2);

        return new long[]{part1, part2};
    }

    public void rewire(HashMap<PositionInSpace, PositionInSpace> connectionsMap, PositionInSpace pos1, PositionInSpace pos2, Set<PositionInSpace> uniquePositions) {
        PositionInSpace root1 = connectionsMap.get(pos1);
        PositionInSpace root2 = connectionsMap.get(pos2);

        // if they are already connected, do nothing
        if (root1.equals(root2)) {
            return;
        }

        for (PositionInSpace key : connectionsMap.keySet()) {
            if (connectionsMap.get(key).equals(root2)) {
                connectionsMap.put(key, root1);
                uniquePositions.remove(key);
            }
        }
        uniquePositions.remove(pos2);
    }

    public double findDistance(PositionInSpace position1, PositionInSpace position2) {
        double dx = position2.x() - position1.x();
        double dy = position2.y() - position1.y();
        double dz = position2.z() - position1.z();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

}

