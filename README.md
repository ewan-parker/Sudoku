# Java Sudoku

## SudokuGame:
A console‑based **Sudoku game** written in Java.  
Solve a 9×9 puzzle by entering your moves, and the game checks for valid moves and detects when you’ve completed the puzzle.

### Features

- Validates each move to ensure:
  - The number isn’t already in the same row  
  - The number isn’t already in the same column  
  - The number isn’t already in the same 3×3 sub‑grid  
- Displays the board neatly with row/column labels and sections for 3×3 boxes.  
- Detects when the puzzle is solved (no empty cells, no duplicates). 

[Example output](Sudoku/SudokuGameExampleRun)

<sub>Note: Example output uses board 1 from [SudokuBoards.txt](Sudoku/src/sudokuBoards.txt) </sub>

---

## SudokuSolver
A **backtracking-based solver** for Sudoku puzzles.  
Given a partially filled 9×9 board, it can automatically find a solution if one exists.

### Features
- Implements a **recursive backtracking algorithm** to solve any valid 9×9 Sudoku puzzle.
- Finds **empty cells** and tries all numbers 1–9, checking for valid moves.
- Can be used with **predefined puzzles** stored in `sudokuBoards.txt`.


[Example output](Sudoku/SolverExampleRun)

<sub>Note: Example output uses board 1 from [SudokuBoards.txt](Sudoku/src/sudokuBoards.txt) </sub>



