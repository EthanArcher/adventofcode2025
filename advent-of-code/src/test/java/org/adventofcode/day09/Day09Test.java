package org.adventofcode.day09;

import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {

    @Test
    void testAgainstExampleInput() {
        Day09 day09 = new Day09(EXAMPLE);
        long[] result = day09.run();
        assertEquals(50, result[0]);
        assertEquals(24, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day09 day09 = new Day09(ACTUAL);
        long[] result = day09.run();
        assertEquals(4738108384L, result[0]);
        assertEquals(1513792010L, result[1]);
    }

}