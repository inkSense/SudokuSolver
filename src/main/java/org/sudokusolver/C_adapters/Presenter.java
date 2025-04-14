package org.sudokusolver.C_adapters;

import javafx.scene.Node;
import javafx.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.D_frameworksAndDrivers.CellView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Presenter {
    private ViewModel model;
    private Presenter2ViewOutputPort presenter2ViewOutputPort;
    private static final Logger log = LoggerFactory.getLogger(Presenter.class);

    public Presenter(ViewModel model, Presenter2ViewOutputPort presenter2ViewOutputPort) {
        this.model = model;
        this.presenter2ViewOutputPort = presenter2ViewOutputPort;
        presenter2ViewOutputPort.setKeyToPressable();
        presenter2ViewOutputPort.refreshBoard(model.getCellList());
    }

    public void setPresenter2ViewOutputPort(Presenter2ViewOutputPort presenter2ViewOutputPort) {
        this.presenter2ViewOutputPort = presenter2ViewOutputPort;
    }



    public void handleKeyPressed(String key) {
        if(key.matches("[1-9]")){
            int value = Integer.parseInt(key);
            log.info("int: " + value);
            List<Point> positions = presenter2ViewOutputPort.getPositionOfClickedCells();
            if(positions.isEmpty()){
                log.info("list of Highlighted Cells is empty");
            } else {
                Point position = positions.get(0);

                for(Cell cell : model.getCellList()){
                    if(cell.position.equals(position)){
                        if(cell.content == value){
                            cell.content = 0;


                        } else {
                            cell.content = value;
                        }
                        break;
                    }
                }
                for (Cell cell : model.getCellList()) {
                    cell.initializePossibleContent();
                }
                model.reducePossibilitiesFromCurrentState();
                presenter2ViewOutputPort.refreshBoard(model.getCellList());
            }
        } else {

            switch (key.toUpperCase()) {
                case "C":
                    // Context
                    model.solveOneStepInContext();
                    model.reducePossibilitiesFromCurrentState();
                    presenter2ViewOutputPort.refreshBoard(model.getCellList());
                    break;
                case "R":
                    // Reduce
                    model.reducePossibilitiesFromCurrentState();
                    presenter2ViewOutputPort.refreshBoard(model.getCellList());
                    break;
                case "S":
                    // Solve
                    model.solveSudokuOneStep();
                    model.reducePossibilitiesFromCurrentState();
                    presenter2ViewOutputPort.refreshBoard(model.getCellList());
                    break;
                default:
                    log.info("Andere Taste: " + key + ". Funktionstasten: C R S ");
            }
        }
    }

    public void cellClicked(int row, int col){
        presenter2ViewOutputPort.highlightCell(row, col);
    }
}
