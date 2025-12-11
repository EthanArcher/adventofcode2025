package org.adventofcode.day10;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adventofcode.day10.ILP.solveWithILP;
import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @Test
    void testAgainstExampleInput() {
        Day10 day10 = new Day10(EXAMPLE);
        long[] result = day10.run();
        assertEquals(7, result[0]);
        assertEquals(33, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day10 day10 = new Day10(ACTUAL);
        long[] result = day10.run();
        assertEquals(466, result[0]);
        assertEquals(17214, result[1]);
    }

    @Test
    void testIncrementingJoltage() {
        Machine machine = new Machine(
                new IndicatorLightDiagram(new char[]{'.', '#', '#', '.'}),
                List.of(
                        new ButtonWiringSchematic(new int[]{3}),
                        new ButtonWiringSchematic(new int[]{1, 3}),
                        new ButtonWiringSchematic(new int[]{2}),
                        new ButtonWiringSchematic(new int[]{2, 3}),
                        new ButtonWiringSchematic(new int[]{0, 2}),
                        new ButtonWiringSchematic(new int[]{0, 1})
                ),
                new JoltageRequirements(new double[]{3, 5, 4, 7})
        );

        int size = machine.joltageRequirements().joltages().length;

        assertArrayEquals(new double[]{0, 0, 0, 1}, machine.buttonWiringSchematics().get(0).wiringJoltageChangers(size));
        assertArrayEquals(new double[]{0, 1, 0, 1}, machine.buttonWiringSchematics().get(1).wiringJoltageChangers(size));
        assertArrayEquals(new double[]{0, 0, 1, 0}, machine.buttonWiringSchematics().get(2).wiringJoltageChangers(size));
        assertArrayEquals(new double[]{0, 0, 1, 1}, machine.buttonWiringSchematics().get(3).wiringJoltageChangers(size));
        assertArrayEquals(new double[]{1, 0, 1, 0}, machine.buttonWiringSchematics().get(4).wiringJoltageChangers(size));
        assertArrayEquals(new double[]{1, 1, 0, 0}, machine.buttonWiringSchematics().get(5).wiringJoltageChangers(size));
    }

    @Test
    void testILPSolver() {
        Machine machine = new Machine(
                new IndicatorLightDiagram(new char[]{'.', '#', '#', '.'}),
                List.of(
                        new ButtonWiringSchematic(new int[]{3}),        // button 0
                        new ButtonWiringSchematic(new int[]{1, 3}),     // button 1
                        new ButtonWiringSchematic(new int[]{2}),        // button 2
                        new ButtonWiringSchematic(new int[]{2, 3}),     // button 3
                        new ButtonWiringSchematic(new int[]{0, 2}),     // button 4
                        new ButtonWiringSchematic(new int[]{0, 1})      // button 5
                ),
                new JoltageRequirements(new double[]{3, 5, 4, 7})
        );

        double[][] A = machine.getButtonMatrix();  // 4 joltages × 6 buttons
        double[] b = machine.joltageRequirements().joltages();  // Target joltages [3, 5, 4, 7]

        // Use ILP to solve: minimize sum(x) subject to A*x = b, x >= 0, x integer
        int[] solution = solveWithILP(A, b);

        System.out.println("ILP Solution: " + java.util.Arrays.toString(solution));

        // Verify the solution
        int totalPresses = java.util.Arrays.stream(solution).sum();
        System.out.println("Total button presses: " + totalPresses);

        // Check that total is optimal (there may be multiple optimal solutions)
        assertEquals(10, totalPresses);

        // Verify that the solution satisfies all joltage constraints
        for (int j = 0; j < A.length; j++) {
            double sum = 0;
            for (int i = 0; i < A[j].length; i++) {
                sum += A[j][i] * solution[i];
            }
            assertEquals(b[j], sum, 0.001, "Joltage " + j + " constraint not satisfied");
        }
    }


}