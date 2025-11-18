# Java Sudoku Game

A console‑based **Sudoku game** written in Java.  
Solve a 9×9 puzzle by entering your moves, and the game checks for valid moves and detects when you’ve completed the puzzle.

> ⚠️ **Early Version:** Currently only allows manual play. Later on I will add a random puzzle generator and a automated solver.

---

[Sample Output](Sudoku/exampleOutput.txt)

---

## Features

- Validates each move to ensure:
  - The number isn’t already in the same row  
  - The number isn’t already in the same column  
  - The number isn’t already in the same 3×3 sub‑grid  
- Displays the board neatly with row/column labels and sections for 3×3 boxes.  
- Detects when the puzzle is solved (no empty cells, no duplicates).  

---
