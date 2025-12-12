package org.adventofcode.day11;

import org.adventofcode.utils.Day;
import org.adventofcode.utils.InputFileType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Day {

    private static final String DAY = "day11";
    private final Map<String, Device> deviceMap;

    public Day11(String fileName, InputFileType inputFileType) {
        super(fileName, inputFileType);
        List<String> lines = readFileAsLines();
        List<Device> devices = lines.stream().map(Device::new).toList();
        this.deviceMap = new HashMap<>();
        for (Device device : devices) {
            deviceMap.put(device.id(), device);
        }
    }

    public long part1() {
        long waysToOutFromYou = waysFromTo("you", "out");
        System.out.println("Part 1: " + waysToOutFromYou);
        return waysToOutFromYou;
    }

    /**
     * Calculates the number of distinct paths from 'svr' (server rack) to 'out' that visit
     * both 'dac' (digital-to-analog converter) and 'fft' (fast Fourier transform) exactly once.
     * 
     * Since both intermediate nodes must be visited, there are only two possible orderings:
     * 
     * Route 1: svr → dac → fft → out
     * Route 2: svr → fft → dac → out
     * 
     * The total paths for each route is the product of paths between consecutive nodes:
     * - Route 1 = paths(svr→dac) × paths(dac→fft) × paths(fft→out)
     * - Route 2 = paths(svr→fft) × paths(fft→dac) × paths(dac→out)
     * 
     * The answer is the sum of paths from both routes.
     */
    public long part2() {
        long route1 = waysFromTo("svr", "dac")
                * waysFromTo("dac", "fft")
                * waysFromTo("fft", "out");

        long route2 = waysFromTo("svr", "fft")
                * waysFromTo("fft", "dac")
                * waysFromTo("dac", "out");

        long result = route1 + route2;
        System.out.println("Part 2: " + result);
        return result;
    }

    private long waysFromTo(String from, String to) {
        Map<String, Long> pathCounts = new HashMap<>();
        pathCounts.put(from, 1L);

        long waysToTarget = 0;
        
        while (!pathCounts.isEmpty()) {
            Map<String, Long> nextPathCounts = new HashMap<>();

            for (Map.Entry<String, Long> entry : pathCounts.entrySet()) {
                String currentDeviceId = entry.getKey();
                long pathCount = entry.getValue();
                Device currentDevice = deviceMap.get(currentDeviceId);
                
                if (currentDevice == null) continue;

                for (String connection : currentDevice.connections()) {
                    if (connection.equals(to)) {
                        waysToTarget += pathCount;
                    } else {
                        nextPathCounts.merge(connection, pathCount, Long::sum);
                    }
                }
            }
            pathCounts = nextPathCounts;
        }
        
        return waysToTarget;
    }


    public long[] run() {
        return new long[]{part1(), part2()};
    }
}
