package org.adventofcode.day11;

import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @Test
    void testAgainstExampleInput() {
        Day11 day11_1 = new Day11("day11_1", EXAMPLE);
        long result_1 = day11_1.part1();
        assertEquals(5, result_1);

        Day11 day11_2 = new Day11("day11_2", EXAMPLE);
        long result_2 = day11_2.part2();
        assertEquals(2, result_2);
    }

    @Test
    void testAgainstActualInput() {
        Day11 day11 = new Day11("day11", ACTUAL);
        long result_1 = day11.part1();
        assertEquals(746L, result_1);
        long result_2 = day11.part2();
        assertEquals(370500293582760L, result_2);
    }

}