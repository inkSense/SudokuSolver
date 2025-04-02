package org.sudokusolver.C_adapters;


import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInputPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {

    UseCaseInputPort useCaseInputPort;

    public Controller(UseCaseInputPort useCaseInputPort) {
        this.useCaseInputPort = useCaseInputPort;
    }

    public void loadSudokusAndPrintThemOut(){
        List<SudokuBoard> sudokus = useCaseInputPort.loadSudokus();
        for(SudokuBoard sudoku : sudokus) {
            sudoku.print();
        }
    }

    public void loadOneSudokuAndSolveIt(){
        List<SudokuBoard> sudokus = useCaseInputPort.loadSudokus();
        for(SudokuBoard sudoku : sudokus){
            if(Objects.equals(sudoku.getDifficulty(), "Easy")){
                useCaseInputPort.solve(sudoku);
            }
        }


    }
}
