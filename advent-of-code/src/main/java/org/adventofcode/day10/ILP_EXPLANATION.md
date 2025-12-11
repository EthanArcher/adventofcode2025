# Integer Linear Programming (ILP) for Button Press Optimization

## What is ILP?

Integer Linear Programming solves optimization problems of the form:

```
minimize:    c₁x₁ + c₂x₂ + ... + cₙxₙ
subject to:  a₁₁x₁ + a₁₂x₂ + ... + a₁ₙxₙ = b₁
             a₂₁x₁ + a₂₂x₂ + ... + a₂ₙxₙ = b₂
             ...
             aₘ₁x₁ + aₘ₂x₂ + ... + aₘₙxₙ = bₘ
             x₁, x₂, ..., xₙ >= 0
             x₁, x₂, ..., xₙ are integers
```

Where:

- **xᵢ** are the decision variables (what we're solving for)
- **cᵢ** are the objective function coefficients (what we're minimizing/maximizing)
- **aᵢⱼ** are the constraint coefficients (the matrix A)
- **bᵢ** are the right-hand side values (the target values)

## Our Button Press Problem

For the button press problem:

- **Variables (x)**: `x[i]` = number of times to press button `i`
- **Objective**: Minimize `sum(x)` = total button presses
- **Constraints**: For each joltage `j`, ensure `sum(A[j][i] * x[i]) = b[j]`

### Example Problem

Given:

- 6 buttons (0-5)
- 4 joltage requirements: [3, 5, 4, 7]
- Matrix A (4×6):
  ```
  Jolt 0: [0, 0, 0, 0, 1, 1]  → buttons 4,5 affect joltage 0
  Jolt 1: [0, 1, 0, 0, 0, 1]  → buttons 1,5 affect joltage 1
  Jolt 2: [0, 0, 1, 1, 1, 0]  → buttons 2,3,4 affect joltage 2
  Jolt 3: [1, 1, 0, 1, 0, 0]  → buttons 0,1,3 affect joltage 3
  ```

This translates to:

```
minimize:    x₀ + x₁ + x₂ + x₃ + x₄ + x₅
subject to:  0x₀ + 0x₁ + 0x₂ + 0x₃ + 1x₄ + 1x₅ = 3  (joltage 0)
             0x₀ + 1x₁ + 0x₂ + 0x₃ + 0x₄ + 1x₅ = 5  (joltage 1)
             0x₀ + 0x₁ + 1x₂ + 1x₃ + 1x₄ + 0x₅ = 4  (joltage 2)
             1x₀ + 1x₁ + 0x₂ + 1x₃ + 0x₄ + 0x₅ = 7  (joltage 3)
             x₀, x₁, x₂, x₃, x₄, x₅ >= 0 (integers)
```

The ILP solver finds: `x = [1, 3, 0, 3, 1, 2]` with total = 10 presses.

## How the ILP Code Works

### Step 1: Load the Solver

```java
System.loadLibrary("jniortools");

MPSolver solver = MPSolver.createSolver("SCIP");
```

- Loads the native library (OR-Tools is written in C++ with Java bindings)
- Creates a SCIP solver (one of several backend engines available)

### Step 2: Create Decision Variables

```java
MPVariable[] x = new MPVariable[numButtons];
for(
int i = 0;
i<numButtons;i++){
x[i]=solver.

makeIntVar(0,1000,"x"+i);
}
```

- Creates one variable per button
- `makeIntVar(min, max, name)`: Integer variable with bounds
- Lower bound = 0 (can't press negative times)
- Upper bound = 1000 (arbitrary large number)

### Step 3: Add Constraints

```java
for(int j = 0;
j<numJoltages;j++){
MPConstraint constraint = solver.makeConstraint(b[j], b[j], "joltage_" + j);
    for(
int i = 0;
i<numButtons;i++){
        constraint.

setCoefficient(x[i], A[j][i]);
    }
            }
```

- One constraint per joltage requirement
- `makeConstraint(lowerBound, upperBound, name)`: Creates constraint with bounds
- Since we want equality (`=`), both bounds are `b[j]`
- `setCoefficient(variable, coefficient)`: Sets `A[j][i]` for each button

### Step 4: Set Objective Function

```java
MPObjective objective = solver.objective();
for(
int i = 0;
i<numButtons;i++){
        objective.

setCoefficient(x[i], 1.0);
}
        objective.

setMinimization();
```

- Creates objective to minimize
- All coefficients are 1.0 (we're minimizing the sum)
- Could use `setMaximization()` if we wanted to maximize instead

### Step 5: Solve

```java
MPSolver.ResultStatus status = solver.solve();
if(status ==MPSolver.ResultStatus.OPTIMAL){
int[] solution = new int[numButtons];
    for(
int i = 0;
i<numButtons;i++){
solution[i]=(int)Math.

round(x[i].solutionValue());
        }
        return solution;
}
```

- `solve()`: Runs the optimization algorithm
- Check status to see if optimal solution found
- Extract values using `variable.solutionValue()`

## Why ILP is Fast

ILP solvers use sophisticated algorithms:

1. **Branch and Bound**: Intelligently explores the solution space
2. **Cutting Planes**: Adds constraints to tighten the feasible region
3. **Presolving**: Simplifies the problem before solving
4. **Heuristics**: Uses smart guesses to find good solutions quickly

For this problem:

- **Search algorithm**: Would try ~10²⁰ combinations (exponential in total presses)
- **ILP solver**: Typically solves in milliseconds (polynomial in problem size)

## Available Solvers

OR-Tools supports multiple backends:

- **SCIP**: Free, open-source, very capable (recommended)
- **GLPK**: Free, simpler, slower
- **CBC**: Free, good middle ground
- **Gurobi**: Commercial, extremely fast (free academic license)
- **CPLEX**: Commercial, extremely fast (free academic license)
