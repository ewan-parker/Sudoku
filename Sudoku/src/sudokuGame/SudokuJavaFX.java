package sudokuGame;


import java.io.InputStream;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class SudokuJavaFX extends Application {

	
	private int lastRow = -1;
	private int lastCol = -1;
	
	
    @Override
    public void start(Stage stage) {
    	
    	Font sudokuFont;
    	try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Crashnumberingserif-KVjW.ttf")) {
    		if (fontStream != null) {
            sudokuFont = Font.loadFont(fontStream, 48); // bigger size
            sudokuFont = Font.font(sudokuFont.getFamily(), javafx.scene.text.FontWeight.BOLD, 48); // bold
    		} else {
            System.out.println("Font file not found! Using default Monospaced");
            sudokuFont = Font.font("Monospaced", javafx.scene.text.FontWeight.BOLD, 48);
    		}
    	} catch (Exception ex) {
        ex.printStackTrace();
        sudokuFont = Font.font("Monospaced", javafx.scene.text.FontWeight.BOLD, 48);
    	}

        SudokuGame model = new SudokuGame(1);
        int[][] board = model.getBoard();
        
        int[][] solution = SudokuSolver.copyBoard(board);
        SudokuSolver.solve(solution); // now solution[][] has the completed puzzle

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
            
                // Restrict input to a single digit 1-9
                TextFormatter<String> formatter = new TextFormatter<>(change -> {
                	String newText = change.getControlNewText();
                	if (newText.isEmpty()) return change;          // allow clearing
                	if (newText.matches("[1-9]")) return change;  // allow 1-9
                	return null;                                  // reject anything else
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
        BorderPane.setMargin(grid, new Insets(50, 0, 0, 75)); // top, right, bottom, left
        

        // --- TOOLBAR ---
        Button newGameBtn = new Button("New Game");
        Button checkBtn = new Button("Check");
        Button solveBtn = new Button("Solve");

        ToolBar toolbar = new ToolBar(newGameBtn, checkBtn, solveBtn);
        
        
        
        checkBtn.setOnAction(e -> {
        	if (lastRow == -1 || lastCol == -1) {
        		System.out.print("No Cell selected yet!");
        		return;
        	}
        	
        	
        	TextField cell = (TextField) getNodeFromGridPane(grid, lastCol, lastRow);
        	String text = cell.getText().trim();
        	
        	if (text.isEmpty()) {
        		System.out.println("Cell is empty.");
        		return;
        	}
        	
        	
        	int value = Integer.parseInt(text);
        	double topWidth = (lastRow % 3 == 0) ? 3 : 0.5;
        	double leftWidth = (lastCol % 3 == 0) ? 3 : 0.5;
        	double bottomWidth = (lastRow == 8) ? 3 : 0.5;
        	double rightWidth = (lastCol == 8) ? 3 : 0.5;

        	String checkedColor = (value == solution[lastRow][lastCol]) ? "lightgreen" : "salmon";
        	cell.setStyle(getCellStyle(topWidth, rightWidth, bottomWidth, leftWidth, checkedColor, cell.getFont()));

        	
        });
        
        // add toolbar at the top
        root.setTop(toolbar);

        // add your grid in the center
        root.setCenter(grid);
        
        grid.setAlignment(Pos.TOP_LEFT);

        Scene scene = new Scene(root, 1500, 900); //8x9 = 720
        stage.setScene(scene);
        stage.setTitle("Sudoku");
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

}