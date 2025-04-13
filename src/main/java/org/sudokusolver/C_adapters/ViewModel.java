package org.sudokusolver.C_adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.Cell;

import java.util.List;

public class ViewModel {
    List<Cell> cellList;
    Controller controller;

    private static final Logger log = LoggerFactory.getLogger(ViewModel.class);
    public ViewModel(Controller controller) {
        this.controller = controller;
        // ToDo - Das kanns irgendwie nicht sein:
        loadSudoku(3); // bitte nicht zu hoch
    }

    public void loadSudoku(int index) {
        controller.loadSudokus();
        controller.setSudokuToUseCaseInputPort(index);
        this.cellList = controller.getSudoku(index).getCells();
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public Controller getController() {
        return controller;
    }

    void solveOneStepInContext(){
        controller.solveOneStepInContext();
    }

    void reducePossibilitiesFromCurrentState(){
        controller.reducePossibilitiesFromCurrentState();
    }

    void solveSudokuOneStep(){
        controller.solveSudokuOneStep();
    }

}
