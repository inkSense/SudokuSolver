package org.sudokusolver.D_frameworksAndDrivers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.C_adapters.Controller;
import org.sudokusolver.C_adapters.Presenter2ViewOutputPort;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FxView implements Presenter2ViewOutputPort {

    private final GridPane grid;
    private final Scene scene;
    private final Controller controller;

    private static final Logger log = LoggerFactory.getLogger(FxView.class);

    public FxView(GridPane grid, Scene scene, Controller controller) {
        this.grid = grid;
        this.scene = scene;
        this.controller = controller;
        addConstraints();
        makeCellViews();
    }

    void addConstraints() {
        // 9 Spalten & 9 Zeilen, jede ~ 66 breit/hoch
        for (int i = 0; i < 9; i++) {
            ColumnConstraints colCon = new ColumnConstraints(66); // 66 oder 65, 70,  ...
            RowConstraints rowCon = new RowConstraints(66);
            grid.getColumnConstraints().add(colCon);
            grid.getRowConstraints().add(rowCon);
        }
    }

    void makeCellViews(){
        for(int col = 0; col < 9; col++){
            for(int row = 0; row < 9; row++){
                CellView cellView = new CellView(new Point(col, row));
                String style = getStyleFromRowAndCol(row, col);
                cellView.setStyle(style);
                int finalCol = col;
                int finalRow = row;
                cellView.setOnMouseClicked(evt -> {
                    controller.cellClicked(finalRow, finalCol);
                    //log.info("Col " + finalCol + ", row " + finalRow);
                });
                grid.add(cellView, col, row);
            }
        }
    }


    public List<Point> highlightCellBasedOnStatus(int row, int col) {
        List<Point> highlightedCells = new ArrayList<>();
        CellView selfCell = getCellByRowColumnIndex(row, col);
        boolean alreadyHighlighted = selfCell.isHighlighted();
        // Alles „unhighlighten“
        for (Node node : grid.getChildren()) {
            if (node instanceof CellView) {
                ((CellView) node).setHighlight(false);
            }
        }
        if(!alreadyHighlighted){
            // Wenn sie nicht schon an war, dann markieren.
            selfCell.setHighlight(true);
            highlightedCells.add(new Point(col, row));
        }
        return highlightedCells;

    }

    public void refreshBoard(List<Cell> cellList) {
        for(Cell cell: cellList){
            CellView cellView = getCellByRowColumnIndex(cell.getPosition().y, cell.getPosition().x);
            if (cell.getContent() == 0) {
                cellView.setValue(null);
                List<Integer> possibleContents = cell.getPossibleContent();
                Set<Integer> possibles = new HashSet<>(possibleContents);
                cellView.setPossibles(possibles);
            } else {
                cellView.setValue(cell.getContent());
            }
        }
    }

    public CellView getCellByRowColumnIndex(int row, int column) {
        for (Node node : grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);
            // Sind rowIndex/colIndex nicht gesetzt, kann es standardmäßig 0 sein:
            if (rowIndex == null) rowIndex = 0;
            if (colIndex == null) colIndex = 0;
            if (rowIndex == row && colIndex == column) {
                return (CellView) node;
            }
        }
        log.error("Something went wrong in getNodeByRowColumnIndex()");
        return null;
    }



    public void setKeyToPressable(){
        scene.setOnKeyPressed(event -> {
            String character = event.getText();
            controller.handleKeyPressed(character);
        });
    }

    String getStyleFromRowAndCol(int row, int col){
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
        return style;
    }

    public Button onButtonClickOpenList(){
        // Erweiterung ListView
        Button openList = new Button("Load Sudoku File");
        openList.setOnAction(e -> {
            SudokuListWindow listWindow = new SudokuListWindow(controller);
            listWindow.show();
        });
        return openList;
    }

    public Button onButtonClickDownload() {
        Button downloadBtn = new Button("Download Sudokus");
        downloadBtn.setOnAction(e -> {
            // Wenn der Aufruf länger dauert ⇒ Task verwenden
            controller.downloadSudokus();
        });
        return downloadBtn;
    }




}
