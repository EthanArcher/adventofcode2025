package org.adventofcode.day02;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 extends Day {

    private static final String DAY = "day02";

    public Day02(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        String input = readFileAsString();
        List<ProductId> productIds = Arrays.stream(input.split(","))
                .map(pid -> new ProductId(pid.split("-")))
                .toList();

        List<String> invalidIdsP1 = new ArrayList<>();
        List<String> invalidIdsP2 = new ArrayList<>();

        for (ProductId pid : productIds) {
            invalidIdsP1.addAll(pid.getInvalidIdsInRangeP1());
            invalidIdsP2.addAll(pid.getInvalidIdsInRangeP2());
        }

        long totalP1 = invalidIdsP1.stream().mapToLong(Long::parseLong).sum();
        long totalP2 = invalidIdsP2.stream().mapToLong(Long::parseLong).sum();

        System.out.println("Total invalid IDs P1: " + totalP1);
        System.out.println("Total invalid IDs P2: " + totalP2);

        return new long[]{totalP1, totalP2};

    }
}

