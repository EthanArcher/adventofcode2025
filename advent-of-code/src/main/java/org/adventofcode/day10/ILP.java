package org.adventofcode.day10;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import static com.google.ortools.Loader.loadNativeLibraries;

public class ILP {

    static {
        loadNativeLibraries();
    }

    /**
     * Solves the Integer Linear Programming problem:
     * minimize: sum(x)
     * subject to: A * x = b
     * x >= 0
     * x are integers
     *
     * @param A The coefficient matrix (m equations × n variables)
     * @param b The right-hand side vector (m values)
     * @return The optimal integer solution (n values)
     */
    public static int[] solveWithILP(double[][] A, double[] b) {
        // Import OR-Tools classes
        MPSolver solver = MPSolver.createSolver("SCIP");

        if (solver == null) {
            throw new IllegalStateException("Could not create SCIP solver");
        }

        int numJoltages = A.length;      // Number of equations (rows)
        int numButtons = A[0].length;    // Number of variables (columns)

        // Create decision variables: x[i] = number of times to press button i
        // Variables are integer and >= 0
        MPVariable[] buttons = new MPVariable[numButtons];
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = solver.makeIntVar(0, 1000, "button" + i);  // Upper bound 1000 is arbitrary
        }

        // Create constraints: for each joltage requirement j, sum(A[j][i] * x[i]) = b[j]
        for (int j = 0; j < numJoltages; j++) {
            MPConstraint constraint = solver.makeConstraint(b[j], b[j], "joltage_" + j);

            for (int i = 0; i < numButtons; i++) {
                constraint.setCoefficient(buttons[i], A[j][i]);
            }
        }

        // Create objective: minimize sum of all button presses
        MPObjective objective = solver.objective();
        for (int i = 0; i < numButtons; i++) {
            objective.setCoefficient(buttons[i], 1.0);  // Coefficient of 1 means we sum all variables
        }
        objective.setMinimization();  // We want to minimize the total

        // Solve the problem
        MPSolver.ResultStatus status = solver.solve();

        if (status == MPSolver.ResultStatus.OPTIMAL) {
            // Extract the solution
            int[] solution = new int[numButtons];
            for (int i = 0; i < numButtons; i++) {
                solution[i] = (int) Math.round(buttons[i].solutionValue());
            }
            return solution;
        } else {
            throw new IllegalStateException("No optimal solution found. Status: " + status);
        }
    }

}
