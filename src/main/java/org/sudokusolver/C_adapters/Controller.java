package org.sudokusolver.C_adapters;


import org.sudokusolver.A_entities.objectsAndDataStructures.SudokuBoard;
import org.sudokusolver.B_useCases.UseCaseInteractor;
import org.sudokusolver.B_useCases.UseCaseOutputPort;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    UseCaseOutputPort useCaseOutputPort;
    UseCaseInteractor useCaseInteractor;

    public Controller(UseCaseInteractor useCaseInteractor) {
        this.useCaseInteractor = useCaseInteractor;
    }

    public Controller(UseCaseOutputPort useCaseOutputPort) {
        this.useCaseOutputPort = useCaseOutputPort;
    }

    public void downloadSudokuAndPrintItOut(){
        SudokuBoard board = useCaseOutputPort.getSudoku();
        board.print();
    }

    public List<SudokuBoard> loadSudokuBoards(){
        List<SudokuBoard> sudokus = new ArrayList<>();
        //sudokus = useCaseInteractor;
        return sudokus;
    }
}
