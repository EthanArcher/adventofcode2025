package org.adventofcode.day08;

import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {

    @Test
    void testAgainstExampleInput() {
        Day08 day08 = new Day08(EXAMPLE);
        long[] result = day08.run(10);
        assertEquals(40, result[0]);
        assertEquals(25272, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day08 day08 = new Day08(ACTUAL);
        long[] result = day08.run(1000);
        assertEquals(67488, result[0]);
        assertEquals(3767453340L, result[1]);
    }

}