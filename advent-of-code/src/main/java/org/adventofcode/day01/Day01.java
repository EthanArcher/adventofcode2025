package org.adventofcode.day01;

import org.adventofcode.utils.Day;

import java.util.List;

public class Day01 extends Day {

    public static void main(String[] args) {
        System.out.println("Hello, Advent of Code Day 01!");
        Day01 day01 = new Day01();
        List<String> input = day01.readFile("day01");

        input.forEach(System.out::println);
    }
}
