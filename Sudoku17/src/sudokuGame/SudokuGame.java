package sudokuGame;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

	public class SudokuGame {

		private int[][] board;
	
	    // Constructor loads puzzle
	    public SudokuGame(int puzzleOption) {
	        this.board = boardLoader(puzzleOption);
	    }
	
	    public int[][] getBoard() {
	        return board;
	    }
	
	    public boolean setValue(int row, int col, int value) {
	        if (validMoveCheck(board, row, col, value)) {
	            board[row][col] = value;
	            return true;
	        }
	        return false;
	    }
	
	    public boolean isSolved() {
	        return puzzleSolved(board);
    }
	    
	    
	    
	    
	    
	
	public static int[][] boardLoader(int puzzleOption) {
		ArrayList<int[][]> boards = new ArrayList<>();
	
	    try (InputStream boardStream = SudokuGame.class.getResourceAsStream("/boards/sudokuBoards.txt")) {
	        if (boardStream == null) {
	            System.out.println("Error: sudokuBoards.txt not found in JAR!");
	            return null;
	        }
	
	        Scanner sc = new Scanner(boardStream);
	
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine().trim();
	            if (line.isEmpty()) continue;
	
	            int[][] board = new int[9][9];
	            for (int i = 0; i < 9; i++) {
	                for (int j = 0; j < 9; j++) {
	                    board[i][j] = Character.getNumericValue(line.charAt(i*9 + j));
	                }
	            }
	            boards.add(board);
	        }
	
	        sc.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	
	    if (boards.isEmpty()) {
	        System.out.println("No boards found in sudokuBoards.txt");
	        return null;
	    }
	
	    if (puzzleOption < 1 || puzzleOption > boards.size()) {
	        System.out.println("Invalid puzzle number. Must be 1-" + boards.size());
	        return null;
	    }
	
	    return boards.get(puzzleOption - 1);
	}
	
	public boolean validMoveCheck(int[][] board, int row, int column, int entry) {
		
		
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
	
	public int[] move() {
		
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Enter row (1-9), column (1-9) and value (1-9): ");
			int row = sc.nextInt();
			int column = sc.nextInt();
			int entry = sc.nextInt();
			
			if ( (row >= 1 && row <= 9) && (column >= 1 && column <= 9) && (entry >= 1 && entry <= 9) ) 
				return new int[] {row - 1, column - 1, entry};
			
			
			System.out.println("Only integers 1 through 9 are allowed. ");
		}
		
	}

	public void printBoard(int[][] board) {
		
		System.out.println("Index|  1  2  3 | 4  5  6 | 7  8  9");
		for (int i = 0; i < 9; i++) {
			
			if (i != 0)
				System.out.println();
			
			
			
			if (i % 3 == 0)
				System.out.print("-----------------------------------");
			
			
			System.out.println();
			
			System.out.print((i + 1) + " -> | ");
			
			for (int j = 0; j < 9; j++) {
				
				if (j % 3 == 0 && j != 0)
					System.out.print("|");
				
				if (board[i][j] == 0)
					System.out.print(" . ");
				else
				System.out.print(" " + board[i][j] + " ");
			}
		
		}
		
		System.out.printf("%n-----------------------------------%n%n");
	}
	
	public boolean puzzleSolved(int[][] board) {
		
		//Check the rows:
		for (int i = 0; i < 9; i++) {
			boolean[] seen = new boolean[10]; // "the seen[] array checks if a number 1-9 has been previously seen"
			
			for (int j = 0; j < 9; j++) {
				
				int value = board[i][j];
				
				if (value == 0)
					return false;
				
				if (seen[value] == true) {
					System.out.println("Duplicate value found on row: " + i); 
					return false; }
				
				seen[value] = true;
				
				
					
				}
				
			}
		
		//Check columns
		for (int j = 0; j < 9; j++) {
			boolean[] seen = new boolean[10]; // "the seen[] array checks if a number 1-9 has been previously seen"
			
			for (int i = 0; i < 9; i++) {
				
				int value = board[i][j];
				
				if (value == 0)
					return false;
				
				if (seen[value] == true) {
					System.out.println("Duplicate value found on row: " + i);
					return false;}
				
				seen[value] = true;
				
				
					
				}
				
			}
	
		
		//Check the 3x3 boxes
		
		for (int boxRow = 0; boxRow < 9; boxRow += 3) {
			for (int boxColumn = 0; boxColumn < 9; boxColumn += 3) {
				
				boolean[] seen = new boolean[10];
				
				for (int i = boxRow; i < boxRow + 3; i++) {
					for (int j = boxColumn; j < boxColumn + 3; j++) {
						
						int value = board[i][j];
				
						if (value == 0)
							return false;
				
						if (seen[value] == true) {
							System.out.println("Duplicate value found in box  " + boxRow + ", " + boxColumn + "...");
							return false;
						}
						seen[value] = true;
						}
					}
				}
		}
		
	return true;
	}
	
	
	


}


