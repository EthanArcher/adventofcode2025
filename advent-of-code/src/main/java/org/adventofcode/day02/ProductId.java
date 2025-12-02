package org.adventofcode.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record ProductId(String[] idRange) {

    public void print() {
        System.out.println(Arrays.toString(idRange));
    }

    public List<String> getInvalidIdsInRangeP1() {
        long start = Long.parseLong(idRange[0]);
        long end = Long.parseLong(idRange[1]);
        List<String> invalidIds = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            String currentId = String.valueOf(i);
            if (!isIdValidP1(currentId)) {
                invalidIds.add(currentId);
            }
        }
        return invalidIds;
    }

    public List<String> getInvalidIdsInRangeP2() {
        long start = Long.parseLong(idRange[0]);
        long end = Long.parseLong(idRange[1]);
        List<String> invalidIds = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            String currentId = String.valueOf(i);
            if (!isIdValidP2(currentId)) {
                invalidIds.add(currentId);
            }
        }
        return invalidIds;
    }

    public static boolean isIdValidP1(String id) {
        // invalid ids need to be of even length
        if (id.length() % 2 != 0) {
            return true;
        }

        // for each letter in the first half of the idRange, check if it matches the second half
        int half = id.length() / 2;
        for (int i = 0; i < half; i++) {
            if (id.charAt(i) != id.charAt(half + i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIdValidP2(String id) {
        int length = id.length();

        if (length == 1) {
            return true;
        }

        // if the idRange is of odd length just check if the first character repeats
        if (length % 2 != 0 && allCharsSame(id)) {
            return false;
        }

        // pattern size goes from 2 up to the half of the idRange length
        for (int pl = 1; pl <= length / 2; pl++) {
            // if the pattern is a multiple of the idRange length
            if (length % pl != 0) {
                continue;
            }

            // how many times the pattern repeats
            String pattern = id.substring(0, pl);
            if (pattern.repeat(length / pl).equals(id)) {
                return false;
            }
        }

        return true;
    }

    private static boolean allCharsSame(String id) {
        char first = id.charAt(0);
        for (int i = 1; i < id.length(); i++) {
            if (id.charAt(i) != first) return false;
        }
        return true;
    }

}
