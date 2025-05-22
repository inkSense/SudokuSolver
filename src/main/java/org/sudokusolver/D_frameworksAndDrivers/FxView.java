package org.sudokusolver.D_frameworksAndDrivers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sudokusolver.A_entities.dataStructures.Position;
import org.sudokusolver.C_adapters.Controller;
import org.sudokusolver.C_adapters.Presenter2ViewOutputPort;
import org.sudokusolver.C_adapters.CellDto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FxView implements Presenter2ViewOutputPort {

    private final GridPane grid;
    private final Scene scene;
    private final Controller controller;
    private static final Logger log = LoggerFactory.getLogger(FxView.class);

    public FxView(Stage stage, Controller controller) {
        this.controller = controller;

        // grid
        this.grid = new GridPane();
        addConstraintsToGrid();
        makeCellViewsAndPutThemIntoGrid();

        // pane
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(this.grid);
        mainPane.setLeft(makeButtonsAndPutItToVerticalBox());

        // scene
        this.scene = new Scene(mainPane, FrameworkConf.sceneX, FrameworkConf.sceneY);
        setSceneToPressableForKeyboard();

        // stage
        stage.setScene(this.scene);
        stage.setTitle("Sudoku Solver");
        stage.show();

    }

    @Override
    public void unHighlightAllCells() {
        for (Node node : grid.getChildren()) {
            if (node instanceof CellView) {
                ((CellView) node).setHighlight(false);
            }
        }
    }

    @Override
    public void highlightCell(Position position) {
        CellView cell = getCellViewByPosition(position);
        cell.setHighlight(true);
    }

    @Override
    public void refreshBoard(List<CellDto> cellList) {
        for(CellDto cell: cellList){
            CellView cellView = getCellViewByPosition(cell.position());
            if (cell.content() == 0) {
                cellView.setValue(null);
                List<Integer> possibleContents = cell.possibles();
                Set<Integer> possibles = new HashSet<>(possibleContents);
                cellView.setPossibles(possibles);
            } else {
                cellView.setValue(cell.content());
            }
            if(cell.valid()){
                cellView.setBlack();
            } else {
                cellView.setRed();
            }
        }
    }

    private void addConstraintsToGrid() {
        // 9 Spalten & 9 Zeilen, jede x breit/hoch
        for (int i = 0; i < 9; i++) {
            ColumnConstraints colCon = new ColumnConstraints(FrameworkConf.gridConstraints);
            RowConstraints rowCon = new RowConstraints(FrameworkConf.gridConstraints);
            grid.getColumnConstraints().add(colCon);
            grid.getRowConstraints().add(rowCon);
        }
    }

    private void makeCellViewsAndPutThemIntoGrid(){
        for(int col = 0; col < 9; col++){
            for(int row = 0; row < 9; row++){
                Position position = new Position(row, col);
                CellView cellView = new CellView();
                String style = getStyle(position);
                cellView.setStyle(style);
                cellView.setOnMouseClicked(evt -> {
                    controller.cellClicked(position);
                });
                grid.add(cellView, col, row); // so!
            }
        }
    }

    private VBox makeButtonsAndPutItToVerticalBox() {
        Button loadButton = onButtonClickOpenFileList(ListWindowMode.load);
        Button saveButton = onButtonClickOpenFileList(ListWindowMode.save);
        Button downloadButton = onButtonClickDownload();
        Button solveButton = onButtonClickSolveSudoku();
        return new VBox(15, loadButton, saveButton, downloadButton, solveButton);
    }

    private void setSceneToPressableForKeyboard(){
        scene.setOnKeyPressed(event -> {
            String character = event.getText();
            controller.handleKeyPressed(character);
        });
    }

    private CellView getCellViewByPosition(Position position) {
        for (Node node : grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            // Sind rowIndex/colIndex nicht gesetzt, kann es standardmäßig 0 sein:
            if (rowIndex == null) rowIndex = 0;
            if (colIndex == null) colIndex = 0;
            if (rowIndex == position.row() && colIndex == position.col()) {
                return (CellView) node;
            }
        }
        log.error("Something went wrong in getNodeByRowColumnIndex()");
        return null;
    }


    private String getStyle(Position position){
        int top = 1, right = 1, bottom = 1, left = 1;
        String topColor = "lightgray", rightColor = "lightgray",
                bottomColor = "lightgray", leftColor = "lightgray";

        // Dickere Linien an 3x3-Grenzen
        if (position.row() % 3 == 0) {
            top = 3;
            topColor = "black";
        }
        if (position.row() == 8) {
            bottom = 3;
            bottomColor = "black";
        }
        if (position.col() % 3 == 0) {
            left = 3;
            leftColor = "black";
        }
        if (position.col() == 8) {
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
        return style;
    }

    private Button onButtonClickOpenFileList(ListWindowMode mode){
        String buttonText = "";
        if(mode == ListWindowMode.load){
            buttonText = "Load Sudoku File";
        } else if(mode == ListWindowMode.save){
            buttonText = "Save Sudoku File";
        } else {
            log.error("Enum not registered in View");
        }
        Button openListButton = new Button(buttonText);
        openListButton.setOnAction(e -> {
            String defaultName = (mode == ListWindowMode.save)
                    ? controller.getLastLoadedFileName()
                    : null;
            FxSudokuListWindow listWindow = new FxSudokuListWindow(controller, mode, defaultName);
            listWindow.show();
        });
        return openListButton;
    }

    private Button onButtonClickDownload() {
        Button downloadBtn = new Button("Download Sudoku");
        downloadBtn.setOnAction(e -> controller.downloadSudoku());
        return downloadBtn;
    }

    private Button onButtonClickSolveSudoku(){
        Button solve = new Button("Solve Sudoku!");
        solve.setOnAction(e -> controller.solveByReasoningAndRecursively());
        return solve;
    }
}
