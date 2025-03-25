package org.sudokusolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Einstiegspunkt für eine JavaFX-Anwendung, die Sudokus von einer API abruft und darstellt
public class SudokuSolver extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku Solver");

        GridPane grid = new GridPane();
        // TODO: Hier Sudoku-Felder darstellen

        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // TODO: API-Aufruf zum Abrufen eines Sudokus
        // TODO: Darstellung des abgerufenen Sudokus
    }

    public static void main(String[] args) {
        launch(args);
    }
}
