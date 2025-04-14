package org.sudokusolver.C_adapters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInputPort;

import java.util.List;
import java.util.stream.Collectors;

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

    public void setFirstEasyOneToUseCaseInputPort(){
        List<SudokuBoard> sudokuList = sudokus.stream().filter(s->s.getDifficulty().equals("Easy")).toList();
        if(!sudokuList.isEmpty()) {
            useCaseInputPort.setSudoku(sudokuList.get(0));
        } else {
            log.error("Didnt find any Easy Sudoku.");
        }
    }

    public void setSudokuToUseCaseInputPort(int sudokuNumber){
        if(sudokuNumber < sudokus.size()) {
            useCaseInputPort.setSudoku(sudokus.get(sudokuNumber));
        } else {
            log.error("Index " + sudokuNumber + " too high.");
        }
    }

    public void loadSudokus(){
        this.sudokus = useCaseInputPort.loadSudokus();
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
