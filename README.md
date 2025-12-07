# Java Sudoku
A sudoku project I made to practice Java & JavaFX

<img width="1413" height="938" alt="image" src="https://github.com/user-attachments/assets/c56868ba-94fd-4187-9423-ccfc1430f26f" />

## Features
- 24 boards
  - 1-4 Very Easy
  - 5-8 Easy
  - 9-12 Intermediate
  - 13-16 Upper-Intermediate
  - 17-20 Hard
  - 21-24 Hardest
- `New Game` Button.
  - scans SudokuBoards.txt file in resources for boards to select from.
  - resets the board and computes new solution
- `Solve` Button.
  - Inputs the correct number and makes the box blue.
  - A cell is not editable after you click solve.

- `Check all` button.
  - Checks all previous entries made by the player
  - Compares to solution, if correct/incorrect the cell will turn green/red respectively
 
- `Check` button.
  - Functions like check all for the last cell clicked

