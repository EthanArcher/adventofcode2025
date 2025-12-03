package org.adventofcode.day03;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {

    @Test
    void testAgainstExampleInput() {
        Day day03 = new Day03(EXAMPLE);
        long[] result = day03.run();
        assertEquals(357, result[0]);
        assertEquals(3121910778619L, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day day03 = new Day03(ACTUAL);
        long[] result = day03.run();
        assertEquals(17383, result[0]);
        assertEquals(172601598658203L, result[1]);
    }

    @Test
    void testFindLargestDigit() {
        assertEquals("9", Day03.findLargestDigit(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 1));
        assertEquals("89", Day03.findLargestDigit(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 2));
        assertEquals("789", Day03.findLargestDigit(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 3));
        assertEquals("9", Day03.findLargestDigit(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, 1));
        assertEquals("98", Day03.findLargestDigit(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, 2));

        assertEquals("954", Day03.findLargestDigit(new int[]{3, 9, 1, 5, 4, 2}, 3));
        assertEquals("987", Day03.findLargestDigit(new int[]{1, 9, 8, 7, 6, 5}, 3));
    }

}
