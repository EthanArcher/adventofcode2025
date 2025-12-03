package org.adventofcode.day03;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.Arrays;
import java.util.List;

public class Day03 extends Day {

    private static final String DAY = "day03";

    public Day03(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {
        List<String> input = readFile();

        long part1Total = 0;
        long part2Total = 0;

        for (String line : input) {

            int[] lineAsIntArray = Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray();

            part1Total += Long.parseLong(findLargestDigit(lineAsIntArray, 2));
            part2Total += Long.parseLong(findLargestDigit(lineAsIntArray, 12));
        }

        System.out.println("Total part 1: " + part1Total);
        System.out.println("Total part 2: " + part2Total);

        return new long[]{part1Total, part2Total};

    }

    /**
     * Recursively constructs the largest possible number by selecting digits from the input sequence.
     * At each step, chooses the largest digit from the allowed range, ensuring enough digits remain for subsequent selections.
     * If only one digit is required, returns the maximum digit.
     * Otherwise, appends the largest digit found and recurses with the remaining sequence and decremented digit count.
     *
     * @param digitSequence  the array of digits to select from
     * @param digitsRequired the number of digits to select
     * @return the largest possible number as a String
     * @throws IllegalArgumentException if input is invalid or digitsRequired is less than 1
     */
    public static String findLargestDigit(int[] digitSequence, int digitsRequired) {
        if (digitSequence == null || digitSequence.length < digitsRequired || digitsRequired < 1) {
            throw new IllegalArgumentException("Invalid input or digitsRequired");
        }

        if (digitsRequired == 1) {
            int maxDigit = Arrays.stream(digitSequence).max().orElse(0);
            return String.valueOf(maxDigit);
        }

        int maxIndex = digitSequence.length - digitsRequired;
        int maxDigit = digitSequence[0];
        int maxDigitIndex = 0;
        for (int i = 1; i <= maxIndex; i++) {
            if (digitSequence[i] > maxDigit) {
                maxDigit = digitSequence[i];
                maxDigitIndex = i;
            }
        }
        int[] remainingDigits = Arrays.copyOfRange(digitSequence, maxDigitIndex + 1, digitSequence.length);
        return String.valueOf(maxDigit) + findLargestDigit(remainingDigits, digitsRequired - 1);
    }

}
