package org.sudokusolver.C_adapters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInputPort;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    UseCaseInputPort useCaseInputPort;
    //List<SudokuBoard> sudokus;
    Presenter presenter;
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    public Controller(UseCaseInputPort useCaseInputPort) {
        this.useCaseInputPort = useCaseInputPort;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void loadSudoku(Path sudokuDatei){
        List<Cell> cells = useCaseInputPort.loadSudoku(sudokuDatei);
        presenter.setCells(cells);
        reducePossibilitiesFromCurrentState();
        presenter.refreshBoard();
    }

    public void solveSudokuOneStep(){
        useCaseInputPort.solveOneStep();
    }

    public void solveOneStepInContext(){
        useCaseInputPort.solveOneStepInContext();
    }

    public void reducePossibilitiesFromCurrentState(){
        useCaseInputPort.reducePossibilitiesFromCurrentState();
    }

    public void handleKeyPressed(String key) {
        if(key.matches("[1-9]")){
            presenter.handleDigit(key);
            reducePossibilitiesFromCurrentState();
            presenter.refreshBoard();
        } else {
            switch (key.toUpperCase()) {
                case "C":
                    // Context
                    solveOneStepInContext();
                    reducePossibilitiesFromCurrentState();
                    presenter.refreshBoard();
                    break;
                case "R":
                    // Reduce
                    reducePossibilitiesFromCurrentState();
                    presenter.refreshBoard();
                    break;
                case "S":
                    // Solve
                    solveSudokuOneStep();
                    reducePossibilitiesFromCurrentState();
                    presenter.refreshBoard();
                    break;
                default:
                    log.info("Andere Taste: " + key + ". Funktionstasten: C R S ");
            }
        }
    }
    public void cellClicked(int row, int col){
        presenter.cellClicked(row, col);
    }
}
