package org.sudokusolver.C_adapters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;
import org.sudokusolver.B_useCases.UseCaseInputPort;

import java.awt.*;
import java.nio.file.Path;
import java.util.List;

public class Controller {

    UseCaseInputPort useCaseInputPort;
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
        useCaseInputPort.reducePossibilitiesFromCurrentState();
        validateAndRefresh();
    }

    public void solveSudokuOneStep(){
        useCaseInputPort.solveOneStep();
        useCaseInputPort.reducePossibilitiesFromCurrentState();
        validateAndRefresh();
    }

    public void solveSudokuOneStepInUnit(){
        useCaseInputPort.solveSudokuOneStepInUnit();
        useCaseInputPort.reducePossibilitiesFromCurrentState();
        validateAndRefresh();
    }

    public void solveByReasoningAsFarAsPossible(){
        useCaseInputPort.solveByReasoningAsFarAsPossible();
        validateAndRefresh();
    }

    public void handleKeyPressed(String key) {
        if(key.matches("[1-9]")){
            handleDigit(key);
        } else {
            switch (key.toUpperCase()) {
                case "A":
                    solveByReasoningAsFarAsPossible();
                    break;
                case "T":
                    // Try
                    solveRecursively();
                    break;
                case "U":
                    // Unit
                    solveSudokuOneStepInUnit();
                    break;
                case "S":
                    // Solve
                    solveSudokuOneStep();
                    break;
                default:
                    log.info("Andere Taste: " + key + ". Funktionstasten: A T U S ");
            }
        }
    }

    void handleDigit(String digitString){
        int value = Integer.parseInt(digitString);
        log.info("int: " + value);
        if(presenter.oneCellClicked()){
            Point clickedCell = presenter.getClickedCells().get(0);
            useCaseInputPort.handleKeyInputWithCellClickedAtPosition(value, clickedCell);
            validateAndRefresh();
        }
    }

    void solveRecursively(){
        List<Cell> cells = useCaseInputPort.solveRecursively();
        presenter.setCells(cells);
        validateAndRefresh();
    }

    public void cellClicked(int row, int col){
        presenter.cellClicked(row, col);
    }

    public void downloadSudoku() {
        useCaseInputPort.downloadSudokuFromApiAndStore();
    }

    private void validateAndRefresh(){
        useCaseInputPort.validateSudoku();
        presenter.refreshBoard();
    }

}
