package org.sudokusolver.C_adapters;


import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInputPort;
import org.sudokusolver.B_useCases.UseCaseInteractor;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    UseCaseInputPort useCaseInputPort;

    public Controller(UseCaseInputPort useCaseInputPort) {
        this.useCaseInputPort = useCaseInputPort;
    }

    public void downloadSudokuAndPrintItOut(){
        List<SudokuBoard> sudokus = useCaseInputPort.loadSudokus();
        for(SudokuBoard sudoku : sudokus) {
            sudoku.print();
        }
    }

    public List<SudokuBoard> loadSudokuBoards(){
        List<SudokuBoard> sudokus = new ArrayList<>();
        //sudokus = useCaseInteractor;
        return sudokus;
    }
}
