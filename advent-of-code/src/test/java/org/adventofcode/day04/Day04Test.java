package org.adventofcode.day04;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {

    @Test
    void testAgainstExampleInput() {
        Day day04 = new Day04(EXAMPLE);
        long[] result = day04.run();
        assertEquals(13, result[0]);
        assertEquals(43, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day day04 = new Day04(ACTUAL);
        long[] result = day04.run();
        assertEquals(1384, result[0]);
        assertEquals(8013, result[1]);
    }
}