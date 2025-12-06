package org.adventofcode.day06;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.adventofcode.utils.Utilities.appendToHashMap;

public class Day06 extends Day {

    private static final String DAY = "day06";

    public Day06(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        List<String> lines = readFile();
        List<char[]> linesAsChars = lines.stream()
                .map(String::toCharArray)
                .toList();

        String operatorLine = lines.getLast();
        String[] operators = lines.getLast().split("\\s+");
        int numberOfEquations = operators.length;

        for (int index = 1; index < operatorLine.length(); index++) {
            char operatorChar = operatorLine.charAt(index);
            if (operatorChar == '+' || operatorChar == '*') {
                for (int line = 0; line < lines.size(); line++) {
                    linesAsChars.get(line)[index - 1] = '|';
                }
            }
        }

        HashMap<Integer, List<String>> equations = new HashMap<>();
        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            String l = new String(linesAsChars.get(lines.indexOf(line)));
            String[] split = l.split("\\|");
            for (int eq = 0; eq < numberOfEquations; eq++) {
                appendToHashMap(equations, eq, split[eq]);
            }
        }

        // modify the values in the map now with Cephalopod math
        HashMap<Integer, List<String>> cephalopodEquations = new HashMap<>();
        equations.forEach((key, values) -> {
            List<String> cephalopod = new ArrayList<>();
            int longest = values.stream().map(String::length).max(Integer::compareTo).get();
            for (int i = 0; i < longest; i++) {
                StringBuilder sb = new StringBuilder();
                for (String v : values) {
                    // account for intellij trimming spaces off the end by checking i against length
                    if (i < v.length()) {
                        sb.append(v.charAt(i));
                    }
                }
                cephalopod.add(sb.toString());
            }
            cephalopodEquations.put(key, cephalopod);
        });

        long part1 = 0;
        long part2 = 0;

        // compute result for part 2
        for (int eq = 0; eq < numberOfEquations; eq++) {
            String operator = operators[eq];
            part1 += computeByOperator(equations.get(eq), operator);
            part2 += computeByOperator(cephalopodEquations.get(eq), operator);
        }

        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);

        return new long[]{part1, part2};
    }

    public static long computeByOperator(List<String> values, String operator) {
        long total = 0;
        for (String value : values) {
            int v = Integer.parseInt(value.trim());
            if (total == 0) {
                total = v;
                continue;
            }

            if (operator.equals("+")) {
                total = total + v;
            } else if (operator.equals("*")) {
                total = total * v;
            }
        }
        return total;
    }
}
