package org.sudokusolver.D_frameworksAndDrivers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
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
        controller.loadSudokus();
        int sudokuIndex = 3;  // bitte nicht zu hoch
        controller.setSudokuToUseCaseInputPort(sudokuIndex);
        sudoku = controller.getSudoku(sudokuIndex);
    }


    @Override
    public void start(Stage primaryStage) {
        grid = new GridPane();

        // 9 Spalten & 9 Zeilen, jede ~ 66 breit/hoch
        for (int i = 0; i < 9; i++) {
            ColumnConstraints colCon = new ColumnConstraints(66); // 66 oder 65, 70,  ...
            RowConstraints rowCon = new RowConstraints(66);
            grid.getColumnConstraints().add(colCon);
            grid.getRowConstraints().add(rowCon);
        }

        //grid.setPadding(new Insets(1, 1, 1, 1));
        //grid.setHgap(3);
        //grid.setVgap(3);

        for(int col = 0; col < 9; col++){
            for(int row = 0; row < 9; row++){
                CellView cellView = new CellView();

                int top = 1, right = 1, bottom = 1, left = 1;
                String topColor = "lightgray", rightColor = "lightgray",
                        bottomColor = "lightgray", leftColor = "lightgray";

                // Dickere Linien an 3x3-Grenzen
                if (row % 3 == 0) {
                    top = 3;
                    topColor = "black";
                }
                if (row == 8) {
                    bottom = 3;
                    bottomColor = "black";
                }
                if (col % 3 == 0) {
                    left = 3;
                    leftColor = "black";
                }
                if (col == 8) {
                    right = 3;
                    rightColor = "black";
                }

                // Nun vier Werte im richtigen Format zusammenbauen:
                String style = String.format(
                        "-fx-border-width: %dpx %dpx %dpx %dpx; "
                                + "-fx-border-color: %s %s %s %s; "
                                + "-fx-border-style: solid solid solid solid;",
                        top, right, bottom, left,
                        topColor, rightColor, bottomColor, leftColor
                );

                // final setzen
                cellView.setStyle(style);


                cellView.setStyle(style);
                grid.add(cellView, col, row);

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
        for(Cell cell: sudoku.getCells()){
            CellView cellView = (CellView) getNodeByRowColumnIndex(cell.position.y, cell.position.x, grid);
            if (cell.content == 0) {
                cellView.setValue(null);
                List<Integer> possibleContents = cell.possibleContent;
                Set<Integer> possibles = new HashSet<>(possibleContents);
                cellView.setPossibles(possibles);
            } else {
                cellView.setValue(cell.content);
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
                case C:
                    controller.solveOneStepInContext();
                    controller.reducePossibilitiesFromCurrentState();
                    refreshBoard();
                    break;
                case S:
                    controller.solveSudokuOneStep();
                    controller.reducePossibilitiesFromCurrentState();
                    refreshBoard();
                    break;
                default:
                    log.info("Andere Taste: " + event.getCode() + ". Funktionstasten: C S ");
            }
        });
    }
}
