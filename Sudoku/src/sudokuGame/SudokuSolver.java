package sudokuGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class SudokuSolver {
	
	public static int[][] boardLoader(String sudokuBoardsFile, int puzzleOption) {
		
		
		
		ArrayList<int[][]> boards = new ArrayList<>();
        
        
        try {
        	Scanner sc = new Scanner(new File("sudokuBoards.txt"));
        	
        	while (sc.hasNextLine()) {
        		String line = sc.nextLine().trim();
        		
        		if (line.isEmpty()) {
        			continue;
        		}
        		
        		
        		
        		if (!line.isEmpty()) {
        			
        			int[][] board = new int[9][9];
        			
        			for (int i = 0; i < 9; i++) {
        				for (int j = 0; j < 9; j++) {
        					
        					board[i][j] = Character.getNumericValue(line.charAt(i*9 + j));
        				}
        			}
        			
        			boards.add(board);
        			
        		}

        		
        		
        		
        	}
        	
        	
        	sc.close();
        } catch (FileNotFoundException e) {
        	System.out.println("Error: sudokuBoards.txt not found!");
        	return null;
        }
        
        if (boards.isEmpty()) {
        System.out.println("No boards found in the sudokuBoards.txt file");
        return null;
        }
        
         
        if (puzzleOption < 1 || puzzleOption > boards.size()) {
        	System.out.println("Invalid puzzle number. Must be 1-" + boards.size());
        	return null;
        }

      
        return boards.get(puzzleOption - 1);
        
		
	}
	
	public static void printBoard(int[][] board) {
		
		
		for (int i = 0; i < 9; i++) {
			
			if (i != 0)
				System.out.println();
			
			
			
			if (i % 3 == 0)
				System.out.print(" +---------+---------+---------+");
			
			
			System.out.println();
			
			System.out.print(" |");
			
			for (int j = 0; j < 9; j++) {
				
				if (j % 3 == 0 && j != 0)
					System.out.print("|");
				
				if (board[i][j] == 0)
					System.out.print(" . ");
				else
				System.out.print(" " + board[i][j] + " ");
			}
			System.out.print("|");
		
		}
		
		System.out.printf("%n +---------+---------+---------+%n%n");
	}
	
	
	
	public static int[] findEmptyCell(int[][] board) {
		
		
		
		
		
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				
				if (board[row][column] == 0) {
					int[] emptyCell =  {row,column};
					return emptyCell;
				}
	
			}
		}
		
		int[] emptyCell =  {-1,-1};
		
		return emptyCell;
	}
	

	public static boolean validMoveCheck(int[][] board, int row, int column, int entry) {
		
		
		//Check all rows:
		for (int j = 0; j < 9; j++) {
		if (entry == board[row][j])
			return false;
				
		} 
		
		//Check the column:
		for (int i = 0; i < 9; i++) {
		if (entry == board[i][column])
			return false; 	
		}
		
		//Check the 3x3 that the entry belongs to:
		
		int startRow = (row/3) * 3;
		int startColumn = (column/3) * 3;
		
		for (int x = startRow; x < startRow + 3; x++) {
			for (int y = startColumn; y < startColumn + 3; y++) {
				if (entry == board[x][y])
					return false;
			}
		}
		
		return true; //Move was valid.
	}
	
	
	public static boolean solve(int[][] board) {
		
		int[] empty = findEmptyCell(board);
		
		if (empty[0] == -1) {
			return true; //puzzle is solved
		}
		
		int row = empty[0];
		int column = empty[1];
		
		for (int entry = 1; entry <= 9; entry++) {
			if ( validMoveCheck(board, row, column, entry) ) {
					board[row][column] = entry;
					
				if (solve(board)) //runs solve(board) again, skips over the number placed as it is no longer 0. 
					return true;
				}
			
			board[row][column] = 0; // if solve(board) ever becomes false it will restart and set the entries to 0, starting at the next value for the empty cell.
		}
		
		return false;
	}
	

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
				
		int puzzleOption = 0;
		
		while (puzzleOption < 1 || puzzleOption > 6) {
			
        System.out.print("Pick a puzzle number (1-6): ");
        
        if (input.hasNextInt()) {
            puzzleOption = input.nextInt();
        } else {
            System.out.println("Please enter a number!");
            input.next(); // clear invalid input
        }
    }

    int[][] board = boardLoader("sudokuBoards.txt", puzzleOption);
		
		System.out.printf("Unsolved Board: %n");
		
		printBoard(board);
			
		boolean solved = solve(board);
			
		if (solved) {
			System.out.printf("%n%nSolved:%n");
			printBoard(board);
		}
		else {
			System.out.printf("%n%nNo Solution");
		}
		
				
	}
	
		

}


