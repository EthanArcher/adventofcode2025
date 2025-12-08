package org.adventofcode.day01;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.List;

public class Day01 extends Day {

    private static final String DAY = "day01";

    public Day01(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {
        List<String> input = readFileAsLines();
        List<Integer> values = input.stream().map(Order::new).map(Order::getAmount).toList();

        int position = 50; // The dial starts by pointing at 50.
        int fullRotationsCounter = 0;
        int landedOnZeroCounter = 0;
        int rolloverCounter = 0;

        for (int value : values) {
//            System.out.println("Current position: " + position + " moving by " + value);

            // if we are going left, consider top as 100, otherwise its 0
            if (position == 0 && value < 0) {
                position = 100;
            }

            if (Math.abs(value) > 100) {
                int fullRotations = Math.abs(value / 100);
                fullRotationsCounter += fullRotations;
//                System.out.println("full rotation " + (value < 0 ? "left " : "right ") + fullRotations + " times");
                value = value % 100;
            }

            position += value;

            if (position == 0 || position == 100) {
                landedOnZeroCounter++;
            }

            if (position > 100 || position < 0) {
                rolloverCounter++;
            }

            position = wrapPosition(position);

//            System.out.println("New position: " + position);
//            System.out.println();
        }

        System.out.println();
        System.out.println("Part One: " + landedOnZeroCounter);
        System.out.println("Part Two: " + (landedOnZeroCounter + rolloverCounter + fullRotationsCounter));

        return new long[]{landedOnZeroCounter, (landedOnZeroCounter + rolloverCounter + fullRotationsCounter)};

    }

    private static int wrapPosition(int position) {
        return (position + 100) % 100;
    }
}

/**
 * Represents a movement order for the dial.
 */
class Order {

    private final int amount;

    public Order(String directionAndAmount) {
        char direction = directionAndAmount.charAt(0);
        int multiplier = direction == 'R' ? 1 : -1;
        int value = Integer.parseInt(directionAndAmount.substring(1));
        this.amount = value * multiplier;
    }

    public int getAmount() {
        return amount;
    }
}
