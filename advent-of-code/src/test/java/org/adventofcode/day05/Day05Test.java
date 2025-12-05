package org.adventofcode.day05;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Test {

    @Test
    void testAgainstExampleInput() {
        Day day05 = new Day05(EXAMPLE);
        long[] result = day05.run();
        assertEquals(3, result[0]);
        assertEquals(14, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day day05 = new Day05(ACTUAL);
        long[] result = day05.run();
        assertEquals(509, result[0]);
        assertEquals(336790092076620L, result[1]);
    }
}