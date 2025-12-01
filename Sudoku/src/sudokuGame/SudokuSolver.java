package sudokuGame;


public class SudokuSolver {
	
		// Copy function
    public static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                copy[r][c] = board[r][c];
            }
        }
        return copy;
    }
	
	
	public static boolean isSolvable(int[][] original) {
        int[][] copy = copyBoard(original);
        return solve(copy);
    }
	
	
	

	public static int[] findEmptyCell(int[][] board) {
	
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
					
				if (board[row][column] == 0) {
					return new int[] {row,column};
				}
			}
		}
		
		return new int[] {-1,-1};
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
	
	
	//The Solver..:
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



}


