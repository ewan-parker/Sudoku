package sudokuGame;

import java.util.List;
import java.util.Optional;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class SudokuJavaFX extends Application {

    @Override
    public void start(Stage stage) {
    	
    	

        SudokuGame model = new SudokuGame(1);
        int[][] board = model.getBoard();

        GridPane grid = new GridPane();
     

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                TextField cell = new TextField();
                cell.setPrefWidth(80);
                cell.setPrefHeight(80);

                // Cell styles
                cell.setStyle("-fx-font-size: 18;" +"-fx-alignment: center;" );

                final int r = row;
                final int c = col;
                
                if (board[row][col] != 0) {
                	cell.setText(String.valueOf(board[row][col]));
                	cell.setEditable(false);
                	cell.setStyle("-fx-background-color: #DDDDDD; -fx-font-size: 18; -fx-alignment: center;");
                	
                }

                cell.setOnMouseClicked(e -> {
                    System.out.println("Clicked cell: " + r + ", " + c);
                    cell.requestFocus();
                });

                grid.add(cell, col, row);
            }
        }

        // Bigger outer background
        BorderPane root = new BorderPane();

        // --- TOOLBAR ---
        Button newGameBtn = new Button("New Game");
        Button checkBtn = new Button("Check");
        Button solveBtn = new Button("Solve");

        ToolBar toolbar = new ToolBar(newGameBtn, checkBtn, solveBtn);

        // add toolbar at the top
        root.setTop(toolbar);

        // add your grid in the center
        root.setCenter(grid);

        Scene scene = new Scene(root, 720, 725); //8x9 = 720
        stage.setScene(scene);
        stage.setTitle("Sudoku");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}