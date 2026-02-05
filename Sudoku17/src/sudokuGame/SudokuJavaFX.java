package sudokuGame;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class SudokuJavaFX extends Application {

	
	private int lastRow = -1;
	private int lastCol = -1;
	
  
    private SudokuGame model;
    private int[][] board;
    private int[][] solution;
    
	
	
    @Override
    public void start(Stage stage) {
    	
    	Font sudokuFont;
    	try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Crashnumberingserif-KVjW.ttf")) {
    		if (fontStream != null) {
            sudokuFont = Font.loadFont(fontStream, 48); 
            sudokuFont = Font.font(sudokuFont.getFamily(), javafx.scene.text.FontWeight.BOLD, 48); 
    		} else {
            System.out.println("Font file not found! Using default Monospaced");
            sudokuFont = Font.font("Monospaced", javafx.scene.text.FontWeight.BOLD, 48);
    		}
    	} catch (Exception ex) {
        ex.printStackTrace();
        sudokuFont = Font.font("Monospaced", javafx.scene.text.FontWeight.BOLD, 48);
    	}

        model = new SudokuGame(1);
        board = model.getBoard();

        solution = SudokuSolver.copyBoard(board);
        SudokuSolver.solve(solution); 
        
        
        GridPane grid = new GridPane();
        
        TextField[][] allCells = new TextField[9][9];
     
        
    
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                TextField cell = new TextField();
                cell.setPrefWidth(85);
                cell.setPrefHeight(85);
                cell.setAlignment(Pos.CENTER);
                cell.setPadding(new Insets(10));
                cell.setFont(sudokuFont);
            
                //single digit 1-9
                TextFormatter<String> formatter = new TextFormatter<>(change -> {
                	String newText = change.getControlNewText();
                	if (newText.isEmpty()) return change;         
                	if (newText.matches("[1-9]")) return change;  
                	return null;                                
                });
                cell.setTextFormatter(formatter);

                final int r = row;
                final int c = col;
                
                if (board[row][col] != 0) {
                	cell.setText(String.valueOf(board[row][col]));
                	cell.setEditable(false);
                	cell.setStyle("-fx-background-color: #DDDDDD;");
                	
                }

                cell.setOnMouseClicked(e -> {
                	lastRow = r;
                	lastCol = c;
                	cell.requestFocus();
                });
                
                allCells[row][col] = cell;

             
                
                double topWidth = (row % 3 == 0) ? 3 : 0.5;
                double leftWidth = (col % 3 == 0) ? 3 : 0.5;
                double bottomWidth = (row == 8) ? 3 : 0.5;
                double rightWidth = (col == 8) ? 3 : 0.5;
                
                String borderStyle = String.format(
                		"-fx-border-width: %f %f %f %f; -fx-border-color: black; -fx-border-style: solid;",
                		topWidth, rightWidth, bottomWidth, leftWidth
                		);

                cell.setStyle(cell.getStyle() + borderStyle);
		        
		        
                grid.add(cell, col, row);
            }
        }

        // Bigger outer background
        BorderPane root = new BorderPane();
        BorderPane.setMargin(grid, new Insets(0, 0, 0, 0)); // top, right, bottom, left
     
        
        
        // TOOLBAR 
        Button newGameBtn = new Button("New Game");
        Button checkBtn = new Button("Check Cell");
        Button solveBtn = new Button("Solve Cell");
        Button checkAllBtn = new Button("Check All");

        ToolBar toolbar = new ToolBar(newGameBtn, checkBtn, solveBtn, checkAllBtn);
        
        
        
        // TOOLBAR BUTTONS
        
        checkBtn.setOnAction(e -> {
        	if (lastRow == -1 || lastCol == -1) {
        		System.out.print("No Cell selected yet!");
        		return;
        	}
        	
        	
        	TextField cell = (TextField) getNodeFromGridPane(grid, lastCol, lastRow);
        	String text = cell.getText().trim();
        	
        	
        	
        	double topWidth = (lastRow % 3 == 0) ? 3 : 0.5;
        	double leftWidth = (lastCol % 3 == 0) ? 3 : 0.5;
        	double bottomWidth = (lastRow == 8) ? 3 : 0.5;
        	double rightWidth = (lastCol == 8) ? 3 : 0.5;
        	
        	if (text.isEmpty()) {
        		cell.setStyle(getCellStyle(topWidth, rightWidth, bottomWidth, leftWidth, "white", cell.getFont()));
        		return;
        	} 
        	
        	int value = Integer.parseInt(text);
        	String checkedColor = (value == solution[lastRow][lastCol]) ? "lightgreen" : "salmon";
        	cell.setStyle(getCellStyle(topWidth, rightWidth, bottomWidth, leftWidth, checkedColor, cell.getFont()));
        	
        	
        	
        });
        
        newGameBtn.setOnAction(e -> {
        	
        	
        	InputStream boardStream = getClass().getResourceAsStream("/boards/sudokuBoards.txt");
        	if (boardStream == null) {
        		throw new RuntimeException("sudokuBoards.txt not found in JAR!");
        	}

        	Scanner sc = new Scanner(boardStream);
        	int totalBoards = 0;
        	while (sc.hasNextLine()) {
        		String line = sc.nextLine().trim();
        		if (!line.isEmpty()) totalBoards++;
        	}

        	
        	List<Integer> puzzleOptions = new ArrayList<>();
        	for (int i = 1; i <= totalBoards; i++) {
        		puzzleOptions.add(i);
        	}
        	
        	sc.close();

        	
        	ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, puzzleOptions);
        	dialog.setTitle("Select Puzzle");
        	dialog.setHeaderText("Pick a puzzle to play:");
        	dialog.setContentText("Puzzle number:");
        	
        
        	
        	InputStream imageStream = getClass().getResourceAsStream("/images/christmasBells.png"); 
        	if (imageStream != null) {
        		ImageView graphic = new ImageView(new javafx.scene.image.Image(imageStream));
        		graphic.setFitWidth(60);
        		graphic.setFitHeight(60);
        		dialog.setGraphic(graphic);
        	}

        	Optional<Integer> result = dialog.showAndWait();
        	result.ifPresent(puzzleNum -> {
        		// Create new game model
        		// Create and assign new model
        		model = new SudokuGame(puzzleNum);
        		board = model.getBoard();

        		// update the solution field (overwrite)
        		solution = SudokuSolver.copyBoard(board);
        		SudokuSolver.solve(solution);

        		// Fill the grid with the new board (this uses allCells)
        		fillGrid(grid, allCells, board, solution);
        	});
        });
        
        
        solveBtn.setOnAction(e -> {
        	if (lastRow == -1 || lastCol == -1) {
        		System.out.println("No cell selected to solve!");
        		return;
        	}

        	TextField cell = allCells[lastRow][lastCol];

        	// Only fill if the cell is editable (not a fixed puzzle cell)
        	if (cell.isEditable()) {
        		int value = solution[lastRow][lastCol];
        	cell.setText(String.valueOf(value));
        	cell.setEditable(false);  // make it non editable after solving

        	double topWidth = (lastRow % 3 == 0) ? 3 : 0.5;
        	double leftWidth = (lastCol % 3 == 0) ? 3 : 0.5;
        	double bottomWidth = (lastRow == 8) ? 3 : 0.5;
        	double rightWidth = (lastCol == 8) ? 3 : 0.5;

        		// Light blue for solved by  button
        	cell.setStyle(getCellStyle(topWidth, rightWidth, bottomWidth, leftWidth, "lightblue", cell.getFont()));
        	
        	}
        });
        
        checkAllBtn.setOnAction(e -> {
        	for (int row = 0; row < 9; row++) {
        		for (int col = 0; col < 9; col++) {
        			TextField cell = allCells[row][col];
        			double topWidth = (row % 3 == 0) ? 3 : 0.5;
        			double leftWidth = (col % 3 == 0) ? 3 : 0.5;
        			double bottomWidth = (row == 8) ? 3 : 0.5;
        			double rightWidth = (col == 8) ? 3 : 0.5;
        			
        			if (!cell.isEditable()) continue;

        			String text = cell.getText().trim();
        			if (text.isEmpty()) {
        				cell.setStyle(getCellStyle(topWidth, rightWidth, bottomWidth, leftWidth, "white", cell.getFont()));
        			} else {
        				int value = Integer.parseInt(text);
        				String checkedColor = (value == solution[row][col]) ? "lightgreen" : "salmon";
        				cell.setStyle(getCellStyle(topWidth, rightWidth, bottomWidth, leftWidth, checkedColor, cell.getFont()));
        			}
        		}
        	}
        });
        
       
        
        
        // add toolbar at the top
        root.setTop(toolbar);

        // add your grid in the center
        root.setCenter(grid);
        
        grid.setAlignment(Pos.CENTER);
        
        InputStream bgStream = getClass().getResourceAsStream("/images/christmasBackground.jpg");
        ImageView bgView = null;
        if (bgStream != null) {
        	javafx.scene.image.Image bgImage = new javafx.scene.image.Image(bgStream);
        	bgView = new ImageView(bgImage);
        	bgView.setFitWidth(1500);
        	bgView.setFitHeight(900);
        	bgView.setPreserveRatio(false);
        	bgView.setScaleX(-1);
        }

        StackPane stack = new StackPane();
        if (bgView != null) stack.getChildren().add(bgView); // background at bottom
        stack.getChildren().add(root); // BorderPane with toolbar, grid on top
	
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/christmasIcon.png")));
        
        
        Scene scene = new Scene(stack, 1400, 900);
        stage.setScene(scene);
        stage.setTitle("Christmas Time Sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static javafx.scene.Node getNodeFromGridPane(GridPane grid, int col, int row) {
	    for (javafx.scene.Node node : grid.getChildren()) {
	        if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
	            return node;
	        }
	    }
	    return null;
    }
    
    private String getCellStyle(double top, double right, double bottom, double left, String bgColor, Font font) {
	    return String.format(
	        "-fx-border-width: %f %f %f %f; " +
	        "-fx-border-color: black; " +
	        "-fx-border-style: solid; " +
	        "-fx-background-color: %s; " +
	        "-fx-alignment: center; " +
	        "-fx-font-family: '%s'; " +
	        "-fx-font-size: %f; " +
	        "-fx-font-weight: bold;",
	        top, right, bottom, left,
	        bgColor,
	        font.getFamily(),
	        font.getSize()
	    );
    }
    
    private void fillGrid(GridPane grid, TextField[][] allCells, int[][] board, int[][] solution) {
	    Font sudokuFont = allCells[0][0].getFont(); // reuse existing font
	
	    for (int row = 0; row < 9; row++) {
	        for (int col = 0; col < 9; col++) {
	            TextField cell = allCells[row][col];
	
	           
	            TextFormatter<String> formatter = new TextFormatter<>(change -> {
	                String newText = change.getControlNewText();
	                if (newText.isEmpty()) return change;
	                if (newText.matches("[1-9]")) return change;
	                return null;
	            });
	            cell.setTextFormatter(formatter);
	
	            if (board[row][col] != 0) {
	                cell.setText(String.valueOf(board[row][col]));
	                cell.setEditable(false);
	                cell.setStyle(getCellStyle(
	                        (row % 3 == 0) ? 3 : 0.5,
	                        (col == 8) ? 3 : 0.5,
	                        (row == 8) ? 3 : 0.5,
	                        (col % 3 == 0) ? 3 : 0.5,
	                        "#DDDDDD",
	                        sudokuFont
	                ));
	            } else {
	                cell.setText("");
	                cell.setEditable(true);
	                cell.setStyle(getCellStyle(
	                        (row % 3 == 0) ? 3 : 0.5,
	                        (col == 8) ? 3 : 0.5,
	                        (row == 8) ? 3 : 0.5,
	                        (col % 3 == 0) ? 3 : 0.5,
	                        "white",
	                        sudokuFont
	                ));
	            }
	        }
	    }
	}
    

}
