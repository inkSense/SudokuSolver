package org.sudokusolver.D_frameworksAndDrivers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInteractor;
import org.sudokusolver.C_adapters.Controller;
import org.sudokusolver.C_adapters.HttpGateway;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class FxView extends Application {


    //@FXML
    GridPane grid;
    Controller controller;
    SudokuBoard sudoku;

    private static final Logger log = LoggerFactory.getLogger(FxView.class);

    public static void main(String... args){
        launch(args);
    }

    @Override
    public void init() {
        var api = new HttpGateway();
        var interactor = new UseCaseInteractor(api);
        controller = new Controller(interactor);
        controller.loadOneEasySudoku();
        sudoku = controller.getSudoku(0);
    }


    @Override
    public void start(Stage primaryStage) {
        grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setPadding(new Insets(1, 1, 1, 1));

        for(int col = 0; col < 9; col++){
            for(int row = 0; row < 9; row++){
                CellView cellView = new CellView();
                cellView.setStyle("-fx-border-color: lightgray; "
                        + "-fx-border-width: 1; "
                        + "-fx-border-style: solid;");
                grid.add(cellView, col, row); // absichtlich anders rum

            }
        }

        refreshBoard();

        // Container, in den wir das Grid legen
        StackPane root = new StackPane(grid);
        // Szene erstellen und im Stage anzeigen
        Scene scene = new Scene(root, 600, 600);
        setSceneEvent(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku Solver");
        primaryStage.show();
    }

    private void refreshBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = sudoku.getCells()[row][col];
                CellView cellView = (CellView) getNodeByRowColumnIndex(row, col, grid);
                if (cell.content == 0) {
                    cellView.setValue(null);
                    List<Integer> possibleContents = cell.getPossibleContent();
                    Set<Integer> possibles = new HashSet<>(possibleContents);
                    cellView.setPossibles(possibles);
                } else {
                    cellView.setValue(cell.content);
                }
            }
        }
    }

    public static Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            // Sind rowIndex/colIndex nicht gesetzt, kann es standardmäßig 0 sein:
            if (rowIndex == null) rowIndex = 0;
            if (colIndex == null) colIndex = 0;
            if (rowIndex == row && colIndex == column) {
                return node;
            }
        }
        log.error("Something went wrong in getNodeByRowColumnIndex()");
        return null;
    }

    private void setSceneEvent(Scene scene){
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case R:
                    controller.reducePossibilitiesFromCurrentState();
                    refreshBoard();
                    break;
                case S:
                    controller.solveSudokuOneStep();
                    refreshBoard();
                    break;
                default:
                    log.info("Andere Taste: " + event.getCode() + ". Drück F für weiter.");
            }
        });
    }
}
