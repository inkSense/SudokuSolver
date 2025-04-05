package org.sudokusolver.C_adapters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInputPort;

import java.util.List;

public class Controller {

    UseCaseInputPort useCaseInputPort;
    List<SudokuBoard> sudokus;
    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    public Controller(UseCaseInputPort useCaseInputPort) {
        this.useCaseInputPort = useCaseInputPort;
    }

    public SudokuBoard getSudoku(int i){
        if( sudokus.size() <= i ) {
            log.error("index too High:");
        }
        return sudokus.get(i);
    }

    public void loadSudokusAndPrintThemOut(){
        List<SudokuBoard> sudokus = useCaseInputPort.loadSudokus();
        for(SudokuBoard sudoku : sudokus) {
            sudoku.print();
        }
    }

    public void loadOneEasySudoku(){
        List<SudokuBoard> sudokuList = useCaseInputPort.loadSudokus();
        sudokuList = sudokuList.stream().filter(s->s.getDifficulty().equals("Easy")).toList();
        sudokus = sudokuList;
        if(!sudokuList.isEmpty()) {
            useCaseInputPort.setSudoku(sudokuList.get(0));
        } else {
            log.error("Found no Easy Sudoku.");
        }
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
}
