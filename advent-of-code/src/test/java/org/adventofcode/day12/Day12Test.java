package org.adventofcode.day12;

import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    @Test
    void testAgainstExampleInput() {
        Day12 day12 = new Day12(EXAMPLE);
        long[] result = day12.run();
        assertEquals(3, result[0]);
    }

    @Test
    void testAgainstActualInput() {
        Day12 day12 = new Day12(ACTUAL);
        long[] result = day12.run();
        assertEquals(485, result[0]);
    }

}