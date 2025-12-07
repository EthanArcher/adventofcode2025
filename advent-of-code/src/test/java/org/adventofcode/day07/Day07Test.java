package org.adventofcode.day07;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Test {

    @Test
    void testAgainstExampleInput() {
        Day day07 = new Day07(EXAMPLE);
        long[] result = day07.run();
        assertEquals(21, result[0]);
        assertEquals(40, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day day07 = new Day07(ACTUAL);
        long[] result = day07.run();
        assertEquals(1703, result[0]);
        assertEquals(171692855075500L, result[1]);
    }

}