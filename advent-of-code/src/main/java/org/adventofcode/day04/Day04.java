package org.adventofcode.day04;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

public class Day04 extends Day {

    private static final String DAY = "day04";

    public Day04(InputFileType inputFileType) {
        super(DAY, inputFileType);
    }

    public long[] run() {

        char[][] grid = readFileAsGrid();

        int part1Total = countCellsWithLessThanFourNeighbors(grid);
        System.out.println("Part 1 Total: " + part1Total);


        int part2Total = removeCellsIteratively(grid);
        System.out.println("Part 2 Total: " + part2Total);

        return new long[]{part1Total, part2Total};
    }

    private int countCellsWithLessThanFourNeighbors(char[][] grid) {
        int part1Total = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (checkSurroundings(grid, i, j) < 4) {
                    part1Total++;
                }
            }
        }
        return part1Total;
    }


    private int removeCellsIteratively(char[][] grid) {
        boolean removed;
        int part2Total = 0;

        do {
            removed = false;
            int removedThisRound = 0;
            char[][] gridAfterRemovals = deepCopyGrid(grid);

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (checkSurroundings(grid, i, j) < 4) {
                        removedThisRound++;
                        gridAfterRemovals[i][j] = '.';
                    }
                }
            }

            if (removedThisRound > 0) {
                removed = true;
                grid = gridAfterRemovals;
                part2Total += removedThisRound;
            }

        } while (removed);
        return part2Total;
    }


    public int checkSurroundings(char[][] grid, int row, int col) {
        if (grid[row][col] == '.') {
            return 8;
        }

        int count = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue;
                }
                int r = dr + row;
                int c = dc + col;

                if (r >= 0 && c >= 0 && r < grid.length && c < grid[r].length) {
                    if (grid[r][c] == '@') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

}
