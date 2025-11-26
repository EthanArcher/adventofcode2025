package org.adventofcode.day01;

import org.adventofcode.utils.Day;

import java.util.List;

public class Day01 {

    public static void main(String[] args) {
        System.out.println("Hello, Advent of Code Day 01!");
        // Day day01 = new Day("example/day01");
        Day day01 = new Day("actual/day01");

        List<String> input = day01.readFile();

        input.forEach(System.out::println);
    }
}
