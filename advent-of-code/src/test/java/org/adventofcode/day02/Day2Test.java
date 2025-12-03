package org.adventofcode.day02;

import org.adventofcode.utils.Day;
import org.junit.jupiter.api.Test;

import static org.adventofcode.utils.InputFileType.ACTUAL;
import static org.adventofcode.utils.InputFileType.EXAMPLE;
import static org.junit.jupiter.api.Assertions.*;

public class Day2Test {

    @Test
    void testAgainstExampleInput() {
        Day day02 = new Day02(EXAMPLE);
        long[] result = day02.run();
        assertEquals(1227775554, result[0]);
        assertEquals(4174379265L, result[1]);
    }

    @Test
    void testAgainstActualInput() {
        Day day02 = new Day02(ACTUAL);
        long[] result = day02.run();
        assertEquals(19128774598L, result[0]);
        assertEquals(21932258645L, result[1]);
    }

    @Test
    void testIsIdValidP1() {
        assertTrue(ProductId.isIdValidP1("12"));
        assertTrue(ProductId.isIdValidP1("1234"));
        assertTrue(ProductId.isIdValidP1("1234321"));

        assertFalse(ProductId.isIdValidP1("1212"));
        assertFalse(ProductId.isIdValidP1("11"));
        assertFalse(ProductId.isIdValidP1("123123"));
    }

    @Test
    void testGettingInvalidIdsInRangeP1() {
        ProductId pid = new ProductId(new String[]{"11", "22"});
        var invalidIds = pid.getInvalidIdsInRangeP1();
        assertTrue(invalidIds.contains("11"));
        assertTrue(invalidIds.contains("22"));
    }

    @Test
    void testIsIdValidP2() {
        assertTrue(ProductId.isIdValidP2("12"));
        assertTrue(ProductId.isIdValidP2("1234"));
        assertTrue(ProductId.isIdValidP2("1234321"));

        assertFalse(ProductId.isIdValidP2("1212"));
        assertFalse(ProductId.isIdValidP2("11"));
        assertFalse(ProductId.isIdValidP2("123123"));
        assertFalse(ProductId.isIdValidP2("1188511885"));
        assertFalse(ProductId.isIdValidP2("824824824"));
    }

}
