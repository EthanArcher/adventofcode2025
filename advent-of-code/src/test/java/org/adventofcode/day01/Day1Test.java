package org.adventofcode.day01;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

public class Day1Test {

    @Test
    void testAgainstExampleInput() {
        Day day01 = new Day01(EXAMPLE);
        day01.run();
    }

    @Test
    void testAgainstActualInput() {
        Day day01 = new Day01(ACTUAL);
        day01.run();
    }

    @Test
    void parsesRightDirection() {
        Order order = new Order("R25");
        assertEquals(25, order.getAmount());
    }

    @Test
    void parsesLeftDirection() {
        Order order = new Order("L10");
        assertEquals(-10, order.getAmount());
    }

    @Test
    void parsesNegativeAmount() {
        Order order = new Order("L100");
        assertEquals(-100, order.getAmount());
    }
}
