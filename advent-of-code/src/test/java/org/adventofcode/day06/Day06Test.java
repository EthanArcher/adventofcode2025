package org.adventofcode.day06;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Test {

    @Test
    void testAgainstExampleInput() {
        Day day06 = new Day06(EXAMPLE);
        long[] result = day06.run();
        assertEquals(4277556, result[0]);
        assertEquals(3263827, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day day06 = new Day06(ACTUAL);
        long[] result = day06.run();
        assertEquals(4719804927602L, result[0]);
        assertEquals(9608327000261L, result[1]);
    }

}